package io.jadon.election

import kotlin.math.abs

/**
 * Represents a political issue a voter or party can take a stance on.
 * Examples include Public Health Care, Climate Change, Abortion
 */
data class Issue(val id: Int, val name: String) {
    constructor(id: Int) : this(id, "Issue #$id")
}

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

    override fun toString(): String =
        when (this) {
            STRONGLY_APPROVE -> "Strongly Approves"
            APPROVE -> "Approves"
            INDIFFERENT -> "Indifferent"
            DISAPPROVE -> "Disapproves"
            STRONGLY_DISAPPROVE -> "Strongly Disapproves"
        }
}

/**
 * Represents a Political Party that can takes stances on issues
 */
data class Party(
    val id: Int,
    val name: String,
    val issueStances: Map<Issue, Policy> = mapOf()
) {
    fun getStance(issue: Issue): Policy =
        this.issueStances.getOrDefault(issue, Policy.INDIFFERENT)

    override fun hashCode(): Int = id

    override fun equals(other: Any?): Boolean {
        if (other !is Party) return false;
        return other.id == this.id
    }

    override fun toString(): String = "$name Party"

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
    val issueStances: MutableMap<Issue, Policy>
) {
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
        val closestParties = if (preferableParties.size <= 3) {
            preferableParties
        } else {
            partiesSortedByDistance.take(3)
        }
        return closestParties.map { it.first }
    }

    override fun toString(): String = name ?: "Voter #$id"

    fun report(): String =
        "${toString()} \n" + issueStances.toList()
            .joinToString("\n") { "    " + it.second.toString() + " of " + it.first.name }
}

data class ElectionModel(val issues: List<Issue>, val parties: List<Party>, val voters: List<Voter>)
