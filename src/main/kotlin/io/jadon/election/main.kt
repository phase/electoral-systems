package io.jadon.election

import kotlin.math.floor

fun main(args: Array<String>) {
    val model = ModelGenerator.createModel(20, 7, 20000)

    val winners = mutableMapOf<Party, Int>()

    val elections = 200

    (1..elections).forEach {
        println("Starting run #$it")

        /*
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
            println("Party Preference for $party: ${voteMap.toList().sortedBy { it.first }
                .map { "#${it.first + 1}: ${it.second}" }}")
        }
         */

        val system = FirstPastThePost(model)

        val (results, votes) = system.cycle()
        val (winner, closeness) = results;

        /*
        partyVoters.clear()
        votes.forEach { vote ->
            vote.preferences.forEachIndexed {index, party ->
                partyVoters.compute(party) { _, map ->
                    val map = map ?: mutableMapOf()
                    map.compute(index) { _, old -> (old ?: 0) + 1 }
                    map
                }
            }
        }

        partyVoters.forEach { (party, voteMap) ->
            println("Votes for $party: ${voteMap.toList().sortedBy { it.first }
                .map { "#${it.first + 1}: ${it.second}" }}")
        }

        println(closeness)
        */
        println("$system Winner: $winner")
        winners.compute(winner) { _, old -> (old ?: 0) + 1}

        /*
        val votesForWinner = system.getPartyPreference(winner, votes)
        val voteAmount = votes.size.toDouble()

        if (votesForWinner.isNotEmpty()) {
            val votesThatHadWinnerAsFirst = votesForWinner[0].second.toDouble()
            val percentageThatHadWinnerAsFirst = floor((votesThatHadWinnerAsFirst / voteAmount) * 100).toInt()
            println("  Percentage that had winner as their first:     $percentageThatHadWinnerAsFirst% (${votesThatHadWinnerAsFirst.toInt()}/${voteAmount.toInt()})")
        }

        if (votesForWinner.size > 1) {
            val votesThatHadWinnerInTopTwo =
                (votesForWinner[0].second + votesForWinner[1].second).toDouble()
            val percentageThatHadWinnerInTopTwo = floor((votesThatHadWinnerInTopTwo / voteAmount) * 100).toInt()
            println("  Percentage that had winner in their top two:   $percentageThatHadWinnerInTopTwo% (${votesThatHadWinnerInTopTwo.toInt()}/${voteAmount.toInt()})")
        }

        if (votesForWinner.size > 2) {
            val votesThatHadWinnerInTopThree =
                (votesForWinner[0].second + votesForWinner[1].second + votesForWinner[2].second).toDouble()
            val percentageThatHadWinnerInTopThree = floor((votesThatHadWinnerInTopThree / voteAmount) * 100).toInt()
            println("  Percentage that had winner in their top three: $percentageThatHadWinnerInTopThree% (${votesThatHadWinnerInTopThree.toInt()}/${voteAmount.toInt()})")
        }
        */

        // val votesThatHadWinnerOnBallot = votes.filter { it.preferences.contains(winner) }.count()
        // val percentageThatHadWinnerOnBallot = floor((votesThatHadWinnerOnBallot / voteAmount) * 100).toInt()
        // println("  Percentage that had winner on their ballot:    $percentageThatHadWinnerOnBallot%")

        val mutations = Mutator.mutate(model)
        println("Mutated model, $mutations mutations occurred")
    }

    println("Wins:")
    winners.toList().sortedBy { it.second }.reversed().forEach { (party, wins) ->
        val percent = floor((wins.toDouble() / elections) * 100).toInt()
        println(" - $party: $wins ($percent%)")
    }
}

fun example() {
    val climateChange = Issue(0, "Climate Change")
    val publicHealthCare = Issue(1, "Public Health Care")
    val abortion = Issue(2, "Abortion")

    val red = Party(
        0, "Red", mutableMapOf(
            climateChange to Policy.STRONGLY_DISAPPROVE,
            publicHealthCare to Policy.DISAPPROVE,
            abortion to Policy.DISAPPROVE
        )
    )
    val blue = Party(
        1, "Blue", mutableMapOf(
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
