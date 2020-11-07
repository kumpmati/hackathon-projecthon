package com.eliteinsmat.hackathonprojecthon

import com.google.android.gms.maps.model.LatLng

data class Restaurant(val name: String, val location: LatLng, val rating: Float, val distance: Int)
