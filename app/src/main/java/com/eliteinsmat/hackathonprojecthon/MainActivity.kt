package com.eliteinsmat.hackathonprojecthon

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.justai.aimybox.Aimybox
import com.justai.aimybox.core.Config
import com.justai.aimybox.dialogapi.jaicf.JAICFDialogApi
import com.justai.aimybox.extensions.stateObservable
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToTextException
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userLocation: LatLng
    var ttsButtonState: Boolean = false
    private val restaurants = ArrayList<Restaurant>();

    @SuppressLint("WrongConstant", "CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tts = createAimybox(applicationContext);

        //maps
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                userLocation = location?.longitude?.let { LatLng(location?.latitude, it) }!!
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                MapsApi.setNewLocation(userLocation)
            }
        GPSUtils(this).turnOnGPS()
//UI

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.recycler) as RecyclerView
        val relativeView = findViewById(R.id.relativeLayout) as RelativeLayout




        fun updateRestaurants(value: ArrayList<Restaurant>){
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                restaurants.clear()
                restaurants.addAll(value)

                val adapter = RestaurantAdapter(restaurants)
                recyclerView.adapter = adapter
                addMarkers(restaurants)
                recyclerView.invalidate();
            })
        }

        //Permission check for recording audio
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION), 1)

            return
        }


        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val button: FloatingActionButton = findViewById(R.id.ttsButton)

        val a = MapsApi.getJuttuasd().subscribe(
            {value -> updateRestaurants(value)
            },
            {error -> println(error)}
        )

        button.setOnClickListener {

            if (ttsButtonState){
                println("STUNTTI SEIS")
                //todo: cancel tts here
            } else {
                ObjectAnimator.ofFloat(relativeView, "translationY", 15f).apply {
                    duration = 220
                    start();
                }

                //Start voice recognition
                //ignore possible exception
                try {
                    tts.startRecognition()
                } catch (e:GooglePlatformSpeechToTextException){
                }
                tts.stateObservable().subscribe(
                    { value ->
                        if(value == Aimybox.State.LISTENING){
                            setButtonState(true)
                        } else {
                            setButtonState(false)

                        }
                    },
                    { error -> println("Error: $error") }        // onError
                )
            }
        }
    }

    //TODO tähän se parse funktio
    private fun parseDate(data: String){
        println("Parsetaan: " + data)
    }

    //create aimybox object for voice recognition
    private fun createAimybox(context: Context): Aimybox {
        val locale = Locale.ENGLISH
        val unitId = UUID.randomUUID().toString()

        val textToSpeech = GooglePlatformTextToSpeech(context, locale) // Or any other TTS
        val speechToText = GooglePlatformSpeechToText(context, locale) // Or any other ASR
        val dialogApi = JAICFDialogApi(unitId, MainScenario.model)

        val config = Config.create(speechToText, textToSpeech, dialogApi)

        return Aimybox(config)
    }

    fun addMarkers(locations: ArrayList<Restaurant>){
        locations.forEach {
            println(it.name)
         mMap.addMarker(MarkerOptions().position(it.location).title(it.name))
        }
    }

    fun setButtonState(state: Boolean){
        val ttsButton = findViewById<FloatingActionButton>(R.id.ttsButton)
        if (state){
            ttsButtonState = true;
            ttsButton.setImageResource(R.drawable.stop)
        } else {
            ttsButtonState = false;
            ttsButton.setImageResource(R.drawable.mic)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true;
    }

}
