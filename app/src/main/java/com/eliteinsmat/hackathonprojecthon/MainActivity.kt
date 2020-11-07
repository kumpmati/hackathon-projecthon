package com.eliteinsmat.hackathonprojecthon

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.justai.aimybox.Aimybox
import com.justai.aimybox.core.Config
import com.justai.aimybox.dialogapi.dialogflow.DialogflowDialogApi
import com.justai.aimybox.extensions.dialogApiEventsObservable
import com.justai.aimybox.extensions.textToSpeechEventsObservable
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToTextException
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tts = createAimybox(applicationContext);
        GPSUtils(this).turnOnGPS()

        //maps
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)





//UI

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recycler) as RecyclerView
        val relativeView = findViewById(R.id.relativeLayout) as RelativeLayout

        //Permission check for recording audio
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
            return
        }



        tts.sendRequest("type: chinese food, location: [-60.4, 23.5]")
        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)



        val button: FloatingActionButton = findViewById(R.id.floatingActionButton)
        button.setOnClickListener {
            val restauraunts = ArrayList<Restaurant>()

            restauraunts.add(Restaurant("res1", LatLng(61.4417671, 22.2842563), 1f, 2));
            restauraunts.add(Restaurant("resdsas2", LatLng(60.4312671, 21.2842563), 2f, 12));
            restauraunts.add(Restaurant("res3213", LatLng(60.2117671, 22.3342563), 3f, 3));
            restauraunts.add(Restaurant("reasds4", LatLng(60.3537671, 22.2841233), 4f, 15));
            restauraunts.add(Restaurant("res35", LatLng(61.3217671, 22.2312563), 4.5f, 91));
            restauraunts.add(Restaurant("res2333316", LatLng(69.4417671, 24.2042563), 5f, 7));
            val adapter = RestaurantAdapter(restauraunts)

            ObjectAnimator.ofFloat(relativeView, "translationY", 15f).apply {
                duration = 220
                start();
            }

            //Start voice recognition
            //ignore possible exception


            try {
                tts.startRecognition()
            } catch (e: GooglePlatformSpeechToTextException){
                tts.startRecognition()
            }



            //shows query sent to dialogflow
            var list = tts.dialogApiEventsObservable()
            list.subscribe(
                { value -> println("Received: $value") },      // onNext
                { error -> println("Error: $error") },         // onError
                { println("Completed") }                       // onComplete
            )

            //dialogflow response
            var list2 = tts.textToSpeechEventsObservable()
            list2.subscribe(
                { value -> parseDate(value.toString()) },      // onNext
                { error -> println("Error2: $error") },         // onError
                { println("Completed2") }                       // onComplete
            )
            
            recyclerView.adapter = adapter
            addMarkers(restauraunts)
        }


    }

    //TODO tähän se parse funktio
    private fun parseDate(data: String){
        println("Parsetaan: " + data)
    }

    //create aimybox object for voice recognition
    private fun createAimybox(context: Context): Aimybox {
        val locale = Locale.ENGLISH

        val textToSpeech = GooglePlatformTextToSpeech(context, locale) // Or any other TTS
        val speechToText = GooglePlatformSpeechToText(context, locale) // Or any other ASR
        val dialogApi = DialogflowDialogApi(context, R.raw.b8584723b0bb, locale.language)

        val config = Config.create(speechToText, textToSpeech, dialogApi)

        return Aimybox(config)
    }

    fun addMarkers(locations: ArrayList<Restaurant>){
        locations.forEach {
            println(it.name)
         mMap.addMarker(MarkerOptions().position(it.location).title(it.name))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true;


    }

}

