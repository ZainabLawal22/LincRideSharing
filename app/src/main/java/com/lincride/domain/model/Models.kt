package com.lincride.domain.model

import com.google.android.gms.maps.model.LatLng

/**
 * Represents a location with coordinates and address information
 */
data class Location(
    val latLng: LatLng,
    val address: String
)

/**
 * Represents a route between two locations with waypoints for animation
 */
data class Route(
    val start: Location,
    val end: Location,
    val waypoints: List<LatLng>,
    val estimatedTime: String
)

/**
 * Represents ride participants
 */
data class Passenger(
    val id: String,
    val name: String,
    val rating: Float,
    val imageUrl: String? = null
)

/**
 * Represents driver information
 */
data class Driver(
    val id: String,
    val name: String,
    val rating: Float,
    val imageUrl: String? = null
)

/**
 * Represents trip earnings breakdown
 */
data class TripEarnings(
    val netEarnings: Double,
    val bonus: Double,
    val commission: Double,
    val distance: Double, // in km
    val helpedRiders: Int
)
