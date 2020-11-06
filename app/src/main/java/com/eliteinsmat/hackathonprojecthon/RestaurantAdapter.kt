package com.eliteinsmat.hackathonprojecthon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(restaurant: Restaurant) {
            val textViewName = itemView.findViewById(R.id.restaurauntName) as TextView
            textViewName.text = restaurant.name
        }
    }
}