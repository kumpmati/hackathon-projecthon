package com.eliteinsmat.hackathonprojecthon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.justai.aimybox.Aimybox
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import java.util.*
import com.justai.aimybox.core.Config
import com.justai.aimybox.dialogapi.dialogflow.DialogflowDialogApi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun createAimybox(context: Context): Aimybox {
        val locale = Locale.getDefault()

        val textToSpeech = GooglePlatformTextToSpeech(context, locale) // Or any other TTS
        val speechToText = GooglePlatformSpeechToText(context, locale) // Or any other ASR
        val dialogApi = DialogflowDialogApi(context, R.raw.b8584723b0bb, locale.language)

        val config = Config.create(speechToText, textToSpeech, dialogApi)

        return Aimybox(config)
    }
}
