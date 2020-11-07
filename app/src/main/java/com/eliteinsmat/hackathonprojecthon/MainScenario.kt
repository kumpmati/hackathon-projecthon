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
        state("NoSushi") { activators { regex(".*Don't.*Sushi*")}
            action {allowedTypes.remove("Japanese")
                reactions.say("How about $allowedTypes") }
        }

        state("Chinese") { activators {
            regex(".*Chinese.*")
            regex(".*Burger.*")
            regex(".*Indian.*")
            regex(".*Japanese.*")
        }
            action {
                MapsApi().query(this.request.input)
                reactions.say("Here are some ${this.request.input} restaurants")
            }
        }

        state("coffee") { activators { regex(".*Coffee.*")}
            action { reactions.say("Here are some coffee shops") }
        }

        state("Ok") { activators { regex(".*OK.*")}
            action {allowedTypes.clear()
                MapsApi().query(this.request.input)
                allowedTypes.addAll(allRestaurants)
                reactions.say("Thank you") }
        }
    }
}
