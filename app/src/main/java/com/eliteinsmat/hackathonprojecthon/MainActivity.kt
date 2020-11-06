package com.eliteinsmat.hackathonprojecthon

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to button

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
    }


}