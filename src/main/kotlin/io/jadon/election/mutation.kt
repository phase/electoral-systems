package io.jadon.election

import kotlin.random.Random

object Chance {

    fun <T> chance(choices: List<Pair<Double, () -> T>>, default: T): T =
        chance(choices) ?: default

    fun <T> chance(choices: List<Pair<Double, () -> T>>): T? {
        val value = Random.nextDouble(100.0)
        var percentage = 0.0
        for (choice in choices) {
            percentage += choice.first
            if (value <= percentage) {
                return choice.second()
            }
        }
        return null
    }

}

object Mutator {

    fun mutate(model: ElectionModel): Int {
        var mutations = 0
        model.voters.forEach { voter ->
            mutations += mutateStances(voter.issueStances)
        }
        model.parties.forEach { party ->
            mutations += mutateStances(party.issueStances)
        }
        return mutations
    }

    private fun mutateStances(stances: MutableMap<Issue, Policy>): Int {
        var mutations = 0
        val newPolicies = mutableMapOf<Issue, Policy>()
        stances.forEach { (issue, policy) ->
            // 5% chance the policy will change greatly
            // 10% chance the policy will change slightly
            //     which translates to:
            // 2.5% chance the policy will move up 2
            // 2.5% chance the policy will move down 2
            // 5% chance the policy will move up 1
            // 5% chance the policy will move down 1
            val newPolicy = Chance.chance(listOf(
                2.5 to { policy.above(2) },
                2.5 to { policy.below(2) },
                5.0 to { policy.above(1) },
                5.0 to { policy.below(1) }
            ), policy)

            if (newPolicy != policy) mutations++
            newPolicies[issue] = newPolicy
        }
        stances.putAll(newPolicies)
        return mutations
    }

}
