package com.eliteinsmat.hackathonprojecthon

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.justai.aimybox.Aimybox
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import java.util.*
import com.justai.aimybox.core.Config
import com.justai.aimybox.dialogapi.dialogflow.DialogflowDialogApi
import com.justai.aimybox.extensions.dialogApiEventsObservable
import com.justai.aimybox.extensions.speechToTextEventsObservable
import com.justai.aimybox.extensions.textToSpeechEventsObservable
import com.justai.aimybox.extensions.voiceTriggerEventsObservable
import com.justai.aimybox.model.Response
import com.justai.aimybox.model.Speech
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tts = createAimybox(applicationContext);

//UI

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recycler) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)



        val button: FloatingActionButton = findViewById(R.id.floatingActionButton)
        button.setOnClickListener {
            val restauraunts = ArrayList<Restaurant>()

            restauraunts.add(Restaurant("res1"));
            restauraunts.add(Restaurant("resdsas2"));
            restauraunts.add(Restaurant("res3213"));
            restauraunts.add(Restaurant("reasds4"));
            restauraunts.add(Restaurant("res35"));
            restauraunts.add(Restaurant("res2333316"));
            val adapter = RestaurantAdapter(restauraunts)

            ObjectAnimator.ofFloat(recyclerView, "translationY", 15f).apply {
                duration = 200
                start();
            }
            recyclerView.adapter = adapter
        }

//END OF UI

        //Permission check for recording audio
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
      tts.startRecognition()
      var list = tts.dialogApiEventsObservable()
      list.subscribe(
              { value -> println("Received: $value") },      // onNext
              { error -> println("Error: $error") },         // onError
              { println("Completed") }                       // onComplete
      )
        var  list2 = tts.textToSpeechEventsObservable()
        list2.subscribe(
                { value -> println("Received2: $value") },      // onNext
                { error -> println("Error2: $error") },         // onError
                { println("Completed2") }                       // onComplete
        )



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
