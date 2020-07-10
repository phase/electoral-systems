package io.jadon.election

import kotlin.random.Random

val voterNames = listOf<String>(
    "Bob",
    "Karen",
    "Alex",
    "Steve",
    "Jack",
    "Tom",
    "Bill",
    "Logan"
)

val partyNames = listOf<String>(
    "Red",
    "Orange",
    "Yellow",
    "Green",
    "Blue",
    "Purple",
    "Indigo",
    "Violet",
    "Cyan",
    "Black",
    "White",
    "Brown",
    "Grey",
    "Pink",
    "Teal",
    "Silver",
    "Gold",
    "Platinum",
    "Diamond",
    "Pearl",
    "Emerald",
    "Sapphire",
    "Ruby",
    "M"
)

object ModelGenerator {
    var issueCount = 0

    var voterCount = 0
    val usedVoterNames = mutableListOf<String>()

    var partyCount = 0
    val usedPartyNames = mutableListOf<String>()

    fun reset() {
        issueCount = 0

        voterCount = 0
        usedVoterNames.clear()

        partyCount = 0
        usedPartyNames.clear()
    }

    fun randomCategory(): Category =
        Category.values()[Random.nextInt(Category.values().size)]

    fun createIssue(): Issue = Issue(randomCategory(), issueCount++)

    fun createIssues(amount: Int): List<Issue> =
        (0..amount).map { createIssue() }

    private fun generateVoterName(id: Int): String {
        if (voterNames.size <= usedVoterNames.size) {
            return "Voter$id"
        }
        val name = voterNames[Random.nextInt(voterNames.size)]
        if (usedVoterNames.contains(name)) return generateVoterName(id)
        usedVoterNames.add(name)
        return name
    }

    private fun generatePartyName(id: Int): String {
        if (partyNames.size <= usedPartyNames.size) {
            return "Party$id"
        }
        val name = partyNames[Random.nextInt(partyNames.size)]
        if (usedPartyNames.contains(name)) return generatePartyName(id)
        usedPartyNames.add(name)
        return name
    }

    private fun generatePolicy(): Policy =
        Policy.values()[Random.nextInt(Policy.values().size)]

    fun createVoter(issues: List<Issue>): Voter {
        val id = voterCount++
        val issueStances = issues.map { it to generatePolicy() }.toMap().toMutableMap()
        return Voter(id, generateVoterName(id), issueStances)
    }

    fun createVoters(amount: Int, issues: List<Issue>): List<Voter> =
        (0 until amount).map { createVoter(issues) }

    fun createParty(issues: List<Issue>): Party {
        val id = partyCount++
        val issueStances = issues.map { it to generatePolicy() }.toMap().toMutableMap()
        return Party(id, generatePartyName(id), issueStances)
    }

    fun createParties(amount: Int, issues: List<Issue>): List<Party> =
        (0 until amount).map { createParty(issues) }

    fun createModel(issues: Int, parties: Int, voters: Int): ElectionModel {
        val issues = createIssues(issues)
        return ElectionModel(issues, createParties(parties, issues), createVoters(voters, issues))
    }
}
