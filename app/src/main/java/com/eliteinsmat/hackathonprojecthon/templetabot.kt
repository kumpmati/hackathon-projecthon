package com.eliteinsmat.hackathonprojecthon

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.googleactions.dialogflow.ActionsDialogflowActivator


val templateBot = BotEngine(
    model = MainScenario.model,
    activators = arrayOf(
        ActionsDialogflowActivator
    )
)