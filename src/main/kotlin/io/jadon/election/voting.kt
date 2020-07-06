package io.jadon.election

import java.lang.IllegalStateException

data class Vote(
    val voter: Voter,
    val preferences: MutableList<Party>
) {
    fun first(): Party? = preferences.firstOrNull()

    fun copy(): Vote = Vote(voter, ArrayList(preferences))
}

fun List<Vote>.copy(): List<Vote> = this.map { it.copy() }

abstract class VotingSystem(
    val name: String,
    val model: ElectionModel
) {
    fun cycle(): Pair<Party, List<Vote>> {
        val votes = getVotes()
        return Pair(chooseWinner(votes), votes)
    }

    private fun getVotes(): List<Vote> {
        return model.voters.map {
            Vote(it, it.findClosestParties(model.parties).toMutableList())
        }
    }

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

    protected abstract fun chooseWinner(votes: List<Vote>): Party

    override fun toString(): String = name
}

class FirstPastThePost(model: ElectionModel) : VotingSystem("First Past The Post", model) {
    override fun chooseWinner(votes: List<Vote>): Party {
        val partyVotes = mutableMapOf<Party, Int>()
        votes.forEach { vote ->
            vote.first()?.let {
                partyVotes.compute(it) { _, old -> (old ?: 0) + 1 }
            }
        }
        // debug stats of votes for each party
        // println(partyVotes.toList().sortedBy { -it.second })
        return partyVotes.maxBy { it.value }?.key ?: throw IllegalStateException("Couldn't compute winner from votes?")
    }
}

class AlternativeVote(model: ElectionModel) : VotingSystem("Alternative Vote", model) {
    override fun chooseWinner(originalVotes: List<Vote>): Party {
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
                return highestParty
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

        return if (remainingParties.isNotEmpty()) {
            remainingParties.first()
        } else {
            model.parties.first()
        }
    }
}
