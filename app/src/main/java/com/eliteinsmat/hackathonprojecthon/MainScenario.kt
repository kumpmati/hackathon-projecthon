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

        val allRestaurants = arrayListOf<String>("Japanese", "Indian", "Hamburger","Chinese")
        var allowedTypes = arrayListOf<String>("Japanese", "Indian", "Hamburger","Chinese")

        state("NoChinese") { activators { regex(".*Don't.*Chinese.*")}
            action {allowedTypes.remove("Chinese")
                reactions.say("How about $allowedTypes") }
        }

        state("NoIndian") { activators { regex(".*Don't.*Indian.*")}
            action {allowedTypes.remove("Indian")
                reactions.say("How about $allowedTypes") }
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

        state("Japanese") { activators { regex(".*Japanese.*")}
            action { reactions.say("Here are some sushi restaurants") }
        }

        state("coffee") { activators { regex(".*Coffee.*")}
            action { reactions.say("Here are some coffee shops") }
        }

        state("Ok") { activators { regex(".*OK.*")}
            action {allowedTypes.clear()
                allowedTypes.addAll(allRestaurants)

                reactions.say("Fresh") }
        }
    }
}