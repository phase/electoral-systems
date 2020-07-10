package io.jadon.election

import java.util.*
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow

/**
 * Categories of issues
 */
enum class Category {
    ECONOMIC, // Equalities <-> Markets
    DIPLOMATIC, // Nation <-> World
    CIVIL, // Liberty <-> Authority
    SOCIETAL // Tradition <-> Progress
}

/**
 * Represents a political issue a voter or party can take a stance on.
 * Examples include Public Health Care, Climate Change, Abortion
 */
data class Issue(val category: Category, val id: Int, val name: String = "Issue #$id")

/**
 * Represents a stance on an issue.
 */
enum class Policy(val value: Double) {
    STRONGLY_DISAPPROVE(-1.5),
    DISAPPROVE(-1.0),
    INDIFFERENT(0.0),
    APPROVE(1.0),
    STRONGLY_APPROVE(1.5),
    ;

    fun approves(): Boolean = value > 0

    fun disapproves(): Boolean = value < 0

    fun above(amount: Int): Policy =
        Policy.values()[(this.ordinal + amount).coerceIn(0, Policy.values().size - 1)]

    fun below(amount: Int): Policy = above(-amount)

    override fun toString(): String =
        when (this) {
            STRONGLY_APPROVE -> "Strongly Approves"
            APPROVE -> "Approves"
            INDIFFERENT -> "Indifferent"
            DISAPPROVE -> "Disapproves"
            STRONGLY_DISAPPROVE -> "Strongly Disapproves"
        }
}

// given a map of stances on issues, calculate what the category policy values are and find the closest ideology
fun Map<Issue, Policy>.ideology(): Ideology {
    val issueCount = this.keys.size
    val min = Policy.STRONGLY_DISAPPROVE.value * issueCount / Category.values().size
    val max = Policy.STRONGLY_APPROVE.value * issueCount / Category.values().size

    val categoryValues = mutableMapOf<Category, Double>()
    this.forEach { (issue, policy) ->
        categoryValues.compute(issue.category) { _, old -> (old ?: 0.0) + policy.value }
    }

     val scaledCategoryValues = categoryValues.map { (category, value) ->
        category to floor((((value - min ) / (max - min)).coerceIn(0.0, 1.0) * 100))
    }.toMap()

    var currentIdeology: Ideology? = null
    var currentDistance = Double.POSITIVE_INFINITY
    for (ideology in Ideology.values()) {
        val distance = scaledCategoryValues.map { (category, value) ->
            abs(ideology.values.getOrDefault(category, 50.0) - value).pow(2.0)
        }.sum()
        if (distance < currentDistance) {
            currentDistance = distance
            currentIdeology = ideology
        }
    }
    return currentIdeology!!
}

/**
 * Represents a Political Party that can takes stances on issues
 */
data class Party(
    val id: Int,
    val name: String,
    val issueStances: MutableMap<Issue, Policy> = mutableMapOf()
) {
    fun getStance(issue: Issue): Policy =
        this.issueStances.getOrDefault(issue, Policy.INDIFFERENT)

    override fun hashCode(): Int = id

    override fun equals(other: Any?): Boolean {
        if (other !is Party) return false;
        return other.id == this.id
    }

    override fun toString(): String = "$name Party (${this.issueStances.ideology()})"

    fun report(): String =
        "${toString()}\n" + issueStances.toList()
            .joinToString("\n") { "    " + it.second.toString() + " of " + it.first.name }
}

/**
 * Represents a human voter that can take stances on issues
 */
data class Voter(
    val id: Int,
    val name: String?,
    val issueStances: MutableMap<Issue, Policy>,
    val memory: Queue<ElectionResults> = LinkedList()
) {

    companion object {
        const val RESULT_MEMORY = 5
    }

    fun getStance(issue: Issue): Policy =
        this.issueStances.getOrDefault(issue, Policy.INDIFFERENT)

    /**
     * Gets the party that most closely aligns with this voter's stances on issues
     */
    fun findClosestParty(parties: List<Party>): Party =
        findClosestParties(parties).first()

    fun findClosestParties(parties: List<Party>): List<Party> {
        val partyDistance = mutableMapOf<Party, Double>()
        for (issueStance in issueStances) {
            val (issue, personalPolicy) = issueStance
            for (party in parties) {
                val policyDifference = abs(personalPolicy.value - party.getStance(issue).value)
                partyDistance.compute(party) { _, old -> (old ?: 0.0) + policyDifference }
            }
        }
        val partiesSortedByDistance = partyDistance.toList().sortedBy { it.second }

        // filter out parties that disagree on 75% of the voter's stances
        val preferableParties = partiesSortedByDistance.filter { it.second < (issueStances.size * 1.25) }

        // if the above filter results in the voter not aligning with any parties, use the first 3 closest parties
        // (this should rarely happen)
        val closestParties = if (preferableParties.isNotEmpty() && preferableParties.size <= 3) {
            preferableParties
        } else {
            partiesSortedByDistance.take(3)
        }
        return closestParties.map { it.first }
    }

    fun vote(parties: List<Party>): Vote {
        val closestParties = findClosestParties(parties)
        if (memory.isEmpty()) {
            return Vote(this, closestParties.toMutableList())
        }

        val vote = Chance.chance(listOf(
            // 25% chance they'll change their vote based on past results
            25.0 to {
                // past support of a party will influence a voter's current vote
                // if the party a voter most aligns with isn't that popular, they will want to change their vote to a party
                // they think has a better chance at winning
                var pastSupport = mutableMapOf<Party, Double>()
                memory.forEach {
                    it.supportPercentage.forEach { (party, support) ->
                        // add up all the support
                        pastSupport.compute(party) { _, old ->
                            (old ?: 0.0) + support
                        }
                    }
                }
                pastSupport = pastSupport.normalize().toMutableMap()

                val newSupport = closestParties.map { it to pastSupport[it] }
                    .filter { it.second != null }
                    .sortedBy { it.second }
                    .reversed()
                    .map { it.first }

                /* debug stats for voter vote change
                val pastWinners = memory.map { it.winner }.joinToString(", ") { it.toString() }
                if (!toString().contains("Voter")) {
                    println(toString() + " memory: " + pastWinners + " \n past support: " + pastSupport + " \n party alignment: " + closestParties)
                    println(" new vote:        $newSupport")
                } */

                newSupport
            }
        ), closestParties)

        return Vote(this, vote.toMutableList())
    }

    fun remember(results: ElectionResults) {
        if (memory.size >= RESULT_MEMORY) {
            // remove the oldest election from memory
            memory.poll()
        }

        // 80% chance they remember this election
        Chance.chance(listOf(
            80.0 to { memory.offer(results) }
        ))
    }

    override fun toString(): String = name ?: "Voter #$id"

    fun report(): String =
        "${toString()} \n" + issueStances.toList()
            .joinToString("\n") { "    " + it.second.toString() + " of " + it.first.name }
}

data class ElectionModel(val issues: List<Issue>, val parties: List<Party>, val voters: List<Voter>)
