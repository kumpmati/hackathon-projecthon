package com.eliteinsmat.hackathonprojecthon

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.net.URL
import kotlin.collections.ArrayList

class MapsApi {
    companion object {
        var location = LatLng(60.4, 23.5)
        private var juttu: BehaviorSubject<ArrayList<Restaurant>> = BehaviorSubject.create()

        fun getJuttuasd(): Observable<ArrayList<Restaurant>> {
            return juttu
        }

        fun setNewLocation(l: LatLng) {
            println(l)
            location = l
        }
    }

    /**
     * Maps query
     */
    fun query(keyword: String): ArrayList<Restaurant> {
        val mapper = jacksonObjectMapper()
        val queryURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.latitude},${location.longitude}&radius=5000&type=restaurant&keyword=${keyword}&key=AIzaSyCOx9CdJs-7V0LEoiFBpwuukHGu2UsdLYo"
        val rawResult = URL(queryURL).readText()
        val rootNode: JsonNode = mapper.readTree(rawResult)
        val results: JsonNode = rootNode.get("results")
        val restaurants: ArrayList<Restaurant> = ArrayList()
        if(results.isArray) {
            for (i in results) {
                val resultName = i.get("name")
                val resultRating = i.get("rating")
                val loc = i.get("geometry").get("location")
                println("$resultName $resultRating $loc")
                val r = Restaurant(resultName.textValue(), LatLng(loc.get("lat").doubleValue(), loc.get("lng").doubleValue()), resultRating.floatValue(), 1)
                restaurants.add(r)
            }
        }

        println("maps api print $restaurants")
        juttu.onNext(restaurants)
        return restaurants
    }
}


