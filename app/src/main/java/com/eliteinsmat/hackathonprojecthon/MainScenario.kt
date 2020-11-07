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
        state("FoodType") {
            activators { regex("chinese") }

            action {
                reactions.say("Here are some chinese restaurants")
            }
        }
    }
}