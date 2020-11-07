package com.eliteinsmat.hackathonprojecthon

import com.justai.jaicf.model.scenario.Scenario

object MainScenario: Scenario() {
    init {
        state("hello") {
            activators {
                regex("hello")
                regex("food")
            }

            action {
                reactions.sayRandom("What would you like to eat?")
            }
        }

        state("Chinese") { activators { regex(".*Chinese.*")}
            action { reactions.say("Here are some chinese restaurants") }
        }

        state("Burger") { activators { regex(".*Burger.*")}
            action { reactions.say("Here are some burger restaurants") }
        }

        state("Indian") { activators { regex(".*Indian.*")}
            action { reactions.say("Here are some indian restaurants") }
        }

        state("Japanese") { activators { regex(".*Sushi.*")}
            action { reactions.say("Here are some sushi restaurants") }
        }

        state("coffee") { activators { regex(".*Coffee.*")}
            action { reactions.say("Here are some coffee shops") }
        }
    }
}