package com.eliteinsmat.hackathonprojecthon

import com.justai.jaicf.model.scenario.Scenario

object MainScenario: Scenario() {
    init {
        state("hello") {
            activators {
                regex(".*hello*.")
                regex(".*food*.")
            }

            action {
                reactions.sayRandom("What would you like to eat?")
            }
        }

        //Food type lists
        val allRestaurants = arrayListOf<String>("Sushi", "Indian", "Hamburger","Chinese", "Pizza", "Kebab")
        var allowedTypes = arrayListOf<String>("Sushi", "Indian", "Hamburger","Chinese", "Pizza", "Kebab")

        //Exclude types
        state("NoChinese") { activators { regex(".*Don't.*Chinese.*")}
            action {allowedTypes.remove("Chinese")
                reactions.say("How about $allowedTypes") }
        }
        state("NoIndian") { activators { regex(".*Don't.*Indian.*")}
            action {allowedTypes.remove("Indian")
                reactions.say("How about $allowedTypes") }
        }
        state("NoSushi") { activators { regex(".*Don't.*Sushi*")}
            action {allowedTypes.remove("Sushi")
                reactions.say("How about $allowedTypes") }
        }
        state("NoPizza") { activators { regex(".*Don't.*Pizza*")}
            action {allowedTypes.remove("Pizza")
                reactions.say("How about $allowedTypes") }
        }
        state("NoKebab") { activators { regex(".*Don't.*Kebab*")}
            action {allowedTypes.remove("Kebab")
                reactions.say("How about $allowedTypes") }
        }

        //Choose types
        state("Chinese") { activators { regex(".*Chinese.*") }
            action {
                MapsApi().query("Chinese")
                reactions.say("Here are some Chinese restaurants")
            }
        }
        state("Sushi") { activators { regex(".*Sushi.*") }
            action {
                MapsApi().query("Sushi")
                reactions.say("Here are some Sushi restaurants")
            }
        }
        state("Kebab") { activators { regex(".*Kebab.*") }
            action {
                MapsApi().query("Kebab")
                reactions.say("Here are some Kebab restaurants")
            }
        }
        state("Pizza") { activators { regex(".*Pizza.*") }
            action {
                MapsApi().query("Pizza")
                reactions.say("Here are some Pizza restaurants")
            }
        }
        state("Indian") { activators { regex(".*Indian.*") }
            action {
                MapsApi().query("Indian")
                reactions.say("Here are some Indian restaurants")
            }
        }
        state("coffee") { activators { regex(".*Coffee.*")}
            action {
                MapsApi().query("Coffee")
                reactions.say("Here are some coffee shops") }
        }

        //Accept
        state("Ok") { activators { regex(".*OK.*")}
            action {allowedTypes.clear()
                //MapsApi().query(this.request.input)
                allowedTypes.addAll(allRestaurants)
                reactions.say("Thank you") }
        }
        fallback { reactions.say("Sorry I didn't get that")}
    }
}
