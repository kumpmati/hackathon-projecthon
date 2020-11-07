package com.eliteinsmat.hackathonprojecthon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Belal on 6/19/2017.
 */

class RestaurantAdapter(val RestaurantList: ArrayList<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {



    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_card, parent, false)

        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RestaurantAdapter.ViewHolder, position: Int) {
        holder.bindItems(RestaurantList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return RestaurantList.size
    }

    fun openNewTabWindow(urls: String, context : Context) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        context.startActivity(intents)
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {
            private var restaurantBS: BehaviorSubject<LatLng> = BehaviorSubject.create()

            fun getRestaurantBS(): BehaviorSubject<LatLng> {
                return restaurantBS
            }
        }



        fun bindItems(restaurant: Restaurant) {
            val textViewName = itemView.findViewById(R.id.restaurauntName) as TextView
            textViewName.text = restaurant.name

            val ratingAmount = itemView.findViewById(R.id.rating) as RatingBar
            ratingAmount.rating = restaurant.rating

            val distanceAmount = itemView.findViewById(R.id.distance) as TextView
            distanceAmount.text = "${restaurant.distance} ⬩ ${restaurant.distance} km"


            val dirButton = itemView.findViewById(R.id.dirButton) as FloatingActionButton
            dirButton.setOnClickListener {
                val uris =
                    Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${restaurant.location.latitude}, ${restaurant.location.longitude}&travelmode=walking")
                val intents = Intent(Intent.ACTION_VIEW, uris)
                val b = Bundle()
                b.putBoolean("new_window", true)
                intents.putExtras(b)
                itemView.context.startActivity(intents)
            }
            val listing = itemView.findViewById(R.id.restaurauntListing) as CardView
            listing.setOnClickListener {
                restaurantBS.onNext(restaurant.location)
            }
        }
    }
}