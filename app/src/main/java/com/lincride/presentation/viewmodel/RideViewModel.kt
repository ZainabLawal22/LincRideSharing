package com.lincride.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.lincride.domain.model.*
import com.lincride.domain.repository.RideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing ride state and handling ride events.
 * Implements event-driven architecture with MVI.
 */
@HiltViewModel
class RideViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {

    // Current state of the ride
    private val _rideState = MutableStateFlow<RideState>(RideState.Idle)
    val rideState: StateFlow<RideState> = _rideState.asStateFlow()

    // Current car position for map animation
    private val _carPosition = MutableStateFlow<LatLng?>(null)
    val carPosition: StateFlow<LatLng?> = _carPosition.asStateFlow()

    // Route waypoints for current journey
    private val _routeWaypoints = MutableStateFlow<List<LatLng>>(emptyList())
    val routeWaypoints: StateFlow<List<LatLng>> = _routeWaypoints.asStateFlow()

    // Pickup and destination locations
    private val _pickupLocation = MutableStateFlow<Location?>(null)
    val pickupLocation: StateFlow<Location?> = _pickupLocation.asStateFlow()

    private val _destinationLocation = MutableStateFlow<Location?>(null)
    val destinationLocation: StateFlow<Location?> = _destinationLocation.asStateFlow()

    // Passengers and driver info
    private val _passengers = MutableStateFlow<List<Passenger>>(emptyList())
    val passengers: StateFlow<List<Passenger>> = _passengers.asStateFlow()

    private val _driver = MutableStateFlow<Driver?>(null)
    val driver: StateFlow<Driver?> = _driver.asStateFlow()

    // Trip earnings
    private val _tripEarnings = MutableStateFlow<TripEarnings?>(null)
    val tripEarnings: StateFlow<TripEarnings?> = _tripEarnings.asStateFlow()

    init {
        handleEvent(RideEvent.AppLoad)
    }

    /**
     * Handle ride events - main entry point for all user and system events
     */
    fun handleEvent(event: RideEvent) {
        viewModelScope.launch {
            when (event) {
                is RideEvent.AppLoad -> handleAppLoad()
                is RideEvent.OfferRideClicked -> handleOfferRideClicked()
                is RideEvent.GetToPickup -> handleGetToPickup()
                is RideEvent.RiderAction -> handleRiderAction(event)
                is RideEvent.HeadingToDestination -> handleHeadingToDestination()
                is RideEvent.TripEnded -> handleTripEnded()
                is RideEvent.ResetSimulation -> handleResetSimulation()
            }
        }
    }

    /**
     * Initialize map with San Francisco center and load initial data
     */
    private fun handleAppLoad() {
        _rideState.value = RideState.Idle
        _pickupLocation.value = rideRepository.getPickupLocation()
        _destinationLocation.value = rideRepository.getDestination()
        _passengers.value = rideRepository.getPassengers()
        _driver.value = rideRepository.getDriver()
        
        // Set initial car position to San Francisco center
        _carPosition.value = LatLng(37.7749, -122.4194)
    }

    /**

     * Transition to offering ride state and show bottom sheet
     */
    private suspend fun handleOfferRideClicked() {
        _rideState.value = RideState.OfferingRide(progress = 0f)
        
        // Small delay before starting animation
        delay(500)
        
        // Automatically start getting to pickup
        handleGetToPickup()
    }

    /**
     * Animate car movement from current location to pickup location
     */
    private suspend fun handleGetToPickup() {
        val route = rideRepository.getRouteToPickup()
        _routeWaypoints.value = route.waypoints

        // Collect progress updates and animate car
        rideRepository.simulateCarProgress(durationMillis = 5000L)
            .collect { progress ->
                // Update state with progress
                _rideState.value = RideState.OfferingRide(progress = progress)

                // Update car position along route
                val waypointIndex = (progress * (route.waypoints.size - 1)).toInt()
                    .coerceIn(0, route.waypoints.size - 1)
                _carPosition.value = route.waypoints[waypointIndex]

                // When animation completes, transition to at pickup state
                if (progress >= 1.0f) {
                    delay(500) // Brief pause at pickup
                    _rideState.value = RideState.AtPickup
                }
            }
    }

    /**
     * Both actions lead to the same next state as per requirements
     */
    private suspend fun handleRiderAction(action: RideEvent.RiderAction) {
        // Brief delay for swipe animation
        delay(300)
        
        // Transition to in progress state
        _rideState.value = RideState.InProgress(progress = 0f)
        
        // Small delay before starting trip
        delay(500)
        
        // Automatically start heading to destination
        handleHeadingToDestination()
    }

    /**
     * Animate car movement from pickup to destination
     */
    private suspend fun handleHeadingToDestination() {
        val route = rideRepository.getRouteToDestination()
        _routeWaypoints.value = route.waypoints

        // Collect progress updates and animate car
        rideRepository.simulateCarProgress(durationMillis = 6000L)
            .collect { progress ->
                // Update state with progress
                _rideState.value = RideState.InProgress(progress = progress)

                // Update car position along route
                val waypointIndex = (progress * (route.waypoints.size - 1)).toInt()
                    .coerceIn(0, route.waypoints.size - 1)
                _carPosition.value = route.waypoints[waypointIndex]

                // When animation completes, transition to completed state
                if (progress >= 1.0f) {
                    delay(500) // Brief pause at destination
                    handleTripEnded()
                }
            }
    }

    /**
     * Handle trip ended
     * Show trip completion overlay with earnings
     */
    private fun handleTripEnded() {
        _tripEarnings.value = rideRepository.getTripEarnings()
        _rideState.value = RideState.Completed
    }

    /**
     * Reset simulation to initial state
     * Allows user to replay the flow
     */
    private fun handleResetSimulation() {
        _rideState.value = RideState.Idle
        _carPosition.value = LatLng(37.7749, -122.4194)
        _routeWaypoints.value = emptyList()
        _tripEarnings.value = null
    }

    /**
     * Trigger pickup confirmation swipe action
     */
    fun onPickupSwipe(didShow: Boolean) {
        val action = if (didShow) {
            RideEvent.RiderAction.PickedUp
        } else {
            RideEvent.RiderAction.DidntShow
        }
        handleEvent(action)
    }
}
