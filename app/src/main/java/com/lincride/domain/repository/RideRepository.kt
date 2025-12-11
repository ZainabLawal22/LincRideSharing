package com.lincride.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.lincride.domain.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing ride data and simulations.
 * Provides data for the ride-sharing flow including routes, locations, and participants.
 */
@Singleton
class RideRepository @Inject constructor() {

    // San Francisco coordinates as specified in requirements
    private val sanFranciscoCenter = LatLng(37.7749, -122.4194)
    
    /**
     * Get the initial pickup location (Ladipo Oluwole Street)
     */
    fun getPickupLocation(): Location {
        return Location(
            latLng = LatLng(37.7849, -122.4094),
            address = "Ladipo Oluwole Street"
        )
    }

    /**
     * Get the destination location (Community Road)
     */
    fun getDestination(): Location {
        return Location(
            latLng = LatLng(37.7649, -122.4294),
            address = "Community Road"
        )
    }

    /**
     * Get the route from driver's current location to pickup
     */
    fun getRouteToPickup(): Route {
        val start = Location(sanFranciscoCenter, "Current Location")
        val end = getPickupLocation()
        
        // Generate waypoints for smooth animation
        val waypoints = generateWaypoints(start.latLng, end.latLng, 20)
        
        return Route(
            start = start,
            end = end,
            waypoints = waypoints,
            estimatedTime = "4 mins"
        )
    }

    /**
     * Get the route from pickup to destination
     */
    fun getRouteToDestination(): Route {
        val start = getPickupLocation()
        val end = getDestination()
        
        // Generate waypoints for smooth animation
        val waypoints = generateWaypoints(start.latLng, end.latLng, 25)
        
        return Route(
            start = start,
            end = end,
            waypoints = waypoints,
            estimatedTime = "4 mins"
        )
    }

    /**
     * Get passenger information
     */
    fun getPassengers(): List<Passenger> {
        return listOf(
            Passenger(
                id = "1",
                name = "Nneka Chukwu",
                rating = 4.7f
            ),
            Passenger(
                id = "2",
                name = "Wade Warren",
                rating = 4.7f
            ),
            Passenger(
                id = "3",
                name = "Brooklyn Simmons",
                rating = 4.7f
            )
        )
    }

    /**
     * Get driver information
     */
    fun getDriver(): Driver {
        return Driver(
            id = "driver_1",
            name = "Daniel Steward",
            rating = 4.7f
        )
    }

    /**
     * Simulate car progress animation from 0 to 1
     * @param durationMillis Total duration of the animation
     * @param updateIntervalMillis How often to emit progress updates
     */
    fun simulateCarProgress(
        durationMillis: Long = 5000L,
        updateIntervalMillis: Long = 50L
    ): Flow<Float> = flow {
        val steps = (durationMillis / updateIntervalMillis).toInt()
        for (i in 0..steps) {
            val progress = i.toFloat() / steps
            emit(progress)
            delay(updateIntervalMillis)
        }
    }

    /**
     * Get trip earnings after completion
     */
    fun getTripEarnings(): TripEarnings {
        return TripEarnings(
            netEarnings = 6600.0,
            bonus = 500.0,
            commission = 500.0,
            distance = 0.86,
            helpedRiders = 4
        )
    }

    /**
     * Generate waypoints between two locations for smooth animation
     */
    private fun generateWaypoints(start: LatLng, end: LatLng, numPoints: Int): List<LatLng> {
        val waypoints = mutableListOf<LatLng>()
        
        for (i in 0..numPoints) {
            val fraction = i.toDouble() / numPoints
            val lat = start.latitude + (end.latitude - start.latitude) * fraction
            val lng = start.longitude + (end.longitude - start.longitude) * fraction
            waypoints.add(LatLng(lat, lng))
        }
        
        return waypoints
    }
}
