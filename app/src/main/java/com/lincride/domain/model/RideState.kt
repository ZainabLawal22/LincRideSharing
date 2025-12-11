package com.lincride.domain.model

/**
 * Represents the current state of the ride flow.
 * Each state corresponds to a specific UI screen from the Figma design.
 */
sealed class RideState {
    /**
    Main map view
     */
    object Idle : RideState()

    /**
     Offer a Ride - Bottom sheet showing driver en route to pickup
     */
    data class OfferingRide(val progress: Float = 0f) : RideState()

    /**
    Pickup Confirmation - Bottom sheet at pickup location
     */
    object AtPickup : RideState()

    /**
    Heading to destination - Bottom sheet showing trip progress
     */
    data class InProgress(val progress: Float = 0f) : RideState()

    /**
     Trip completed - Overlay showing trip summary
     */
    object Completed : RideState()
}
