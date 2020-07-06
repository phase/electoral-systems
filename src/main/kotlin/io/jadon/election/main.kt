package io.jadon.election

import kotlin.math.floor

fun main(args: Array<String>) {
    val model = ModelGenerator.createModel(20, 7, 20000)

    val partyVoters = mutableMapOf<Party, MutableMap<Int, Int>>()
    model.voters.forEach { voter ->
        voter.findClosestParties(model.parties).forEachIndexed { index, party ->
            partyVoters.compute(party) { _, map ->
                val map = map ?: mutableMapOf()
                map.compute(index) { _, old -> (old ?: 0) + 1 }
                map
            }
        }
    }
    partyVoters.forEach { (party, voteMap) ->
        println("$party Vote Spread: ${voteMap.toList().sortedBy { it.first }}")
    }

    val systems = listOf(FirstPastThePost(model), AlternativeVote(model))

    systems.map { system ->
        val (winner, votes) = system.cycle()

        val votesForWinner = system.getPartyPreference(winner, votes)

        val voteAmount = votes.size.toDouble()
        val votesThatHadWinnerAsFirst = votesForWinner[0].second.toDouble()
        val votesThatHadWinnerInTopTwo =
            (votesForWinner[0].second + votesForWinner[1].second).toDouble()
        val votesThatHadWinnerInTopThree =
            (votesForWinner[0].second + votesForWinner[1].second + votesForWinner[2].second).toDouble()
        val votesThatHadWinnerOnBallot = votes.filter { it.preferences.contains(winner) }.count()

        val percentageThatHadWinnerAsFirst = floor((votesThatHadWinnerAsFirst / voteAmount) * 100)
        val percentageThatHadWinnerInTopTwo = floor((votesThatHadWinnerInTopTwo / voteAmount) * 100)
        val percentageThatHadWinnerInTopThree = floor((votesThatHadWinnerInTopThree / voteAmount) * 100)
        val percentageThatHadWinnerOnBallot = floor((votesThatHadWinnerOnBallot / voteAmount) * 100)

        println("$system Winner: $winner")
        println("  Percentage that had winner as their first:     $percentageThatHadWinnerAsFirst%")
        println("  Percentage that had winner in their top two:   $percentageThatHadWinnerInTopTwo%")
        println("  Percentage that had winner in their top three: $percentageThatHadWinnerInTopThree%")
        // println("  Percentage that had winner on their ballot:    $percentageThatHadWinnerOnBallot%")
    }
}

fun example() {
    val climateChange = Issue(0, "Climate Change")
    val publicHealthCare = Issue(1, "Public Health Care")
    val abortion = Issue(2, "Abortion")

    val red = Party(
        0, "Red", mapOf(
            climateChange to Policy.STRONGLY_DISAPPROVE,
            publicHealthCare to Policy.DISAPPROVE,
            abortion to Policy.DISAPPROVE
        )
    )
    val blue = Party(
        1, "Blue", mapOf(
            climateChange to Policy.STRONGLY_APPROVE,
            publicHealthCare to Policy.APPROVE,
            abortion to Policy.STRONGLY_APPROVE
        )
    )

    val bob = Voter(
        0, "Bob", mutableMapOf(
            climateChange to Policy.APPROVE,
            publicHealthCare to Policy.INDIFFERENT,
            abortion to Policy.STRONGLY_DISAPPROVE
        )
    )

    println(bob.name + " votes for the " + bob.findClosestParty(listOf(red, blue)))
}
