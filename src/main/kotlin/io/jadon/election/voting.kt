package io.jadon.election

/**
 * A voter's vote in an election
 */
data class Vote(
    val voter: Voter,
    val preferences: MutableList<Party>
) {
    fun first(): Party? = preferences.firstOrNull()

    fun copy(): Vote = Vote(voter, ArrayList(preferences))
}

fun List<Vote>.copy(): List<Vote> = this.map { it.copy() }

/**
 * Normalize a list of double into 0 -> 1
 */
fun <T> Map<T, Double>.normalize(): Map<T, Double> {
    if (this.keys.isEmpty()) return this
    val ratio = 1 / this.maxBy { it.value }!!.value
    return this.mapValues { it.value * ratio }
}

/**
 * Results of an election. The support percentage map shows how close each party was to winning.
 */
data class ElectionResults(val winner: Party, val supportPercentage: Map<Party, Double>)

abstract class VotingSystem(
    val name: String,
    val model: ElectionModel
) {
    fun cycle(): Pair<ElectionResults, List<Vote>> {
        val votes = getVotes()
        val results = chooseWinner(votes)
        model.voters.forEach { it.remember(results) }
        return Pair(results, votes)
    }

    private fun getVotes(): List<Vote> =
        model.voters.map { it.vote(model.parties) }

    fun getPartyPreference(party: Party, votes: List<Vote>): List<Pair<Int, Int>> {
        val voteRanks = mutableMapOf<Int, Int>()
        votes.forEach {
            it.preferences.forEachIndexed { index, other ->
                if (party == other) {
                    voteRanks.compute(index + 1) { _, old -> (old ?: 0) + 1 }
                }
            }
        }
        return voteRanks.toList().sortedBy { it.first }
    }

    protected abstract fun chooseWinner(votes: List<Vote>): ElectionResults

    override fun toString(): String = name
}

class FirstPastThePost(model: ElectionModel) : VotingSystem("First Past The Post", model) {
    override fun chooseWinner(votes: List<Vote>): ElectionResults {
        val partyVotes = mutableMapOf<Party, Int>()
        votes.forEach { vote ->
            vote.first()?.let {
                partyVotes.compute(it) { _, old -> (old ?: 0) + 1 }
            }
        }

        // closeness = how many votes the party got / how many voters their were
        val closeness = partyVotes.mapValues { it.value.toDouble() / votes.size }.normalize()
        // debug stats of votes for each party
        // println(partyVotes.toList().sortedBy { -it.second })
        val winner = partyVotes.maxBy { it.value }!!.key
        return ElectionResults(winner, closeness)
    }
}

class AlternativeVote(model: ElectionModel) : VotingSystem("Alternative Vote", model) {
    override fun chooseWinner(originalVotes: List<Vote>): ElectionResults {
        // closeness = how many people preferred the party
        val closeness = model.parties.map {
            it to (getPartyPreference(it, originalVotes)
                // collect eac
                .map { (it.second.toDouble() / originalVotes.size) / (it.first * 0.75) }.sum())
        }.toMap().normalize()

        val votes = originalVotes.copy()
        val voteAmount = votes.size

        val discardedParties = mutableListOf<Party>()

        while (discardedParties.size < model.parties.size - 1) {
            val partyVotes = mutableMapOf<Party, Int>()
            votes.forEach { vote ->
                vote.first()?.let {
                    partyVotes.compute(it) { _, old -> (old ?: 0) + 1 }
                }
            }

            val highestParty = partyVotes.maxBy { it.value }!!.key
            val lowestParty = partyVotes.minBy { it.value }!!.key

            val votesForHighestParty = getPartyPreference(highestParty, votes)[0].second
            // need >= 50% to win
            if (votesForHighestParty >= voteAmount / 2) {
                return ElectionResults(highestParty, closeness)
            }

            // debug stats for the lowest & highest parties
            // println(" > " + highestParty + " had " + votesForHighestParty + "/" + voteAmount + " votes")
            // println(" > " + highestParty + " had " + (Math.floor(100.0 * (votesForHighestParty.toDouble() / voteAmount.toDouble()))) + "% of the vote")
            // println(" > Removing lowest party " + lowestParty + " with " +  partyVotes.minBy { it.value }!!.value + " votes")

            // remove the votes for the lowest party
            votes.forEach { vote ->
                // find the vote preference indexes for the lowest party
                val preferencesToRemove = mutableListOf<Int>()
                vote.preferences.forEachIndexed { index, party ->
                    if (party == lowestParty) {
                        preferencesToRemove.add(index)
                    }
                }
                // reverse the list and remove the indexes that match the party
                preferencesToRemove.sort()
                preferencesToRemove.reverse()
                preferencesToRemove.forEach { vote.preferences.removeAt(it) }
            }
            discardedParties.add(lowestParty)
        }
        val remainingParties = model.parties.toMutableList()
        remainingParties.removeAll(discardedParties)

        val winner = if (remainingParties.isNotEmpty()) {
            remainingParties.first()
        } else {
            model.parties.first()
        }
        return ElectionResults(winner, closeness)
    }
}
