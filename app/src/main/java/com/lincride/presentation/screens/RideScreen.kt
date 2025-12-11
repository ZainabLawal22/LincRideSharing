package com.lincride.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lincride.domain.model.RideEvent
import com.lincride.domain.model.RideState
import com.lincride.presentation.components.*
import com.lincride.presentation.viewmodel.RideViewModel

/**
 * Ride screen that drives all UI states and transitions

 */
@Composable
fun RideScreen(
    viewModel: RideViewModel = hiltViewModel()
) {
    val rideState by viewModel.rideState.collectAsStateWithLifecycle()
    val carPosition by viewModel.carPosition.collectAsStateWithLifecycle()
    val routeWaypoints by viewModel.routeWaypoints.collectAsStateWithLifecycle()
    val pickupLocation by viewModel.pickupLocation.collectAsStateWithLifecycle()
    val destinationLocation by viewModel.destinationLocation.collectAsStateWithLifecycle()
    val passengers by viewModel.passengers.collectAsStateWithLifecycle()
    val driver by viewModel.driver.collectAsStateWithLifecycle()
    val tripEarnings by viewModel.tripEarnings.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background: Map is always visible
        MapScreen(
            carPosition = carPosition,
            routeWaypoints = routeWaypoints,
            onOfferRideClick = {
                viewModel.handleEvent(RideEvent.OfferRideClicked)
            }
        )

        // Overlay: Bottom sheets and overlays based on state
        when (val state = rideState) {
            is RideState.Idle -> {
                // No overlay - just the map with controls
            }

            is RideState.OfferingRide -> {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300)),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    OfferRideBottomSheet(
                        progress = state.progress,
                        driver = driver,
                        passengers = passengers,
                        estimatedTime = "4 mins",
                        pickupLocation = pickupLocation?.address ?: "Ladipo Oluwole Street"
                    )
                }
            }

            is RideState.AtPickup -> {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300)),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    PickupConfirmationBottomSheet(
                        passenger = passengers.firstOrNull(),
                        pickupLocation = pickupLocation?.address ?: "Ladipo Oluwole Street",
                        waitingTime = "04:45",
                        onSwipe = { didShow ->
                            viewModel.onPickupSwipe(didShow)
                        }
                    )
                }
            }

            is RideState.InProgress -> {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300)),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    HeadingToDestinationBottomSheet(
                        progress = state.progress,
                        passengers = passengers,
                        estimatedTime = "4 mins",
                        destination = destinationLocation?.address ?: "Community Road"
                    )
                }
            }

            is RideState.Completed -> {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300))
                ) {
                    TripCompletedOverlay(
                        earnings = tripEarnings,
                        passengers = passengers,
                        onDismiss = {
                            viewModel.handleEvent(RideEvent.ResetSimulation)
                        },
                        onNewTrip = {
                            viewModel.handleEvent(RideEvent.ResetSimulation)
                        }
                    )
                }
            }
        }
    }
}
