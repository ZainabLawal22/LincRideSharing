package com.lincride.domain.model

/**
 * Sealed class representing all possible ride events in the application.
 * This follows an event-driven architecture pattern for state management.
 */
sealed class RideEvent {
    /**
    Application launches and displays the main map view
     */
    object AppLoad : RideEvent()

    /**
     * Triggers the display of the offer ride bottom sheet
     */
    object OfferRideClicked : RideEvent()

    /**
     * Includes car animation progress on map
     */
    data class GetToPickup(val progress: Float = 0f) : RideEvent()

    /**
     * Can be "Didn't Show" or "Picked Up" (both lead to same state for this assessment)
     */
    sealed class RiderAction : RideEvent() {
        object DidntShow : RiderAction()
        object PickedUp : RiderAction()
    }

    /**

     * Includes car animation progress on map
     */
    data class HeadingToDestination(val progress: Float = 0f) : RideEvent()

    /**
     * Trip has completed successfully
     */
    object TripEnded : RideEvent()

    /**
     * Reset simulation to initial state
     */
    object ResetSimulation : RideEvent()
}
