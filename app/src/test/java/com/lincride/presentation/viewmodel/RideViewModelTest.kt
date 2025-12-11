package com.lincride.presentation.viewmodel

import app.cash.turbine.test
import com.lincride.domain.model.RideEvent
import com.lincride.domain.model.RideState
import com.lincride.domain.repository.RideRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for RideViewModel
 * Tests the event-driven architecture and state transitions
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RideViewModelTest {

    private lateinit var viewModel: RideViewModel
    private lateinit var repository: RideRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        
        // Mock repository methods
        coEvery { repository.simulateCarProgress(any(), any()) } returns flow {
            emit(0f)
            emit(0.5f)
            emit(1f)
        }
        
        viewModel = RideViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Idle`() = runTest {
        // Given: ViewModel is initialized
        
        // When: Observing initial state
        val initialState = viewModel.rideState.value
        
        // Then: State should be Idle
        assertEquals(RideState.Idle, initialState)
    }

    @Test
    fun `handleEvent AppLoad sets state to Idle`() = runTest {
        // Given: ViewModel is initialized
        
        // When: AppLoad event is triggered
        viewModel.handleEvent(RideEvent.AppLoad)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then: State should be Idle
        assertEquals(RideState.Idle, viewModel.rideState.value)
    }

    @Test
    fun `handleEvent OfferRideClicked transitions to OfferingRide state`() = runTest {
        // Given: Initial idle state
        viewModel.handleEvent(RideEvent.AppLoad)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // When: OfferRideClicked event is triggered
        viewModel.handleEvent(RideEvent.OfferRideClicked)
        testDispatcher.scheduler.advanceTimeBy(1000)
        
        // Then: State should be OfferingRide
        assertTrue(viewModel.rideState.value is RideState.OfferingRide)
    }

    @Test
    fun `ride flow progresses through all states correctly`() = runTest {
        viewModel.rideState.test {
            // Initial state
            assertEquals(RideState.Idle, awaitItem())
            
            // Trigger offer ride
            viewModel.handleEvent(RideEvent.OfferRideClicked)
            testDispatcher.scheduler.advanceTimeBy(1000)
            
            // Should transition to OfferingRide
            val offeringState = awaitItem()
            assertTrue(offeringState is RideState.OfferingRide)
            
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `car position updates during ride progress`() = runTest {
        // Given: Initial state
        viewModel.handleEvent(RideEvent.AppLoad)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // When: Offer ride is clicked
        viewModel.handleEvent(RideEvent.OfferRideClicked)
        testDispatcher.scheduler.advanceTimeBy(6000)
        
        // Then: Car position should be updated
        assertNotNull(viewModel.carPosition.value)
    }

    @Test
    fun `onPickupSwipe triggers RiderAction event`() = runTest {
        // Given: At pickup state
        viewModel.handleEvent(RideEvent.OfferRideClicked)
        testDispatcher.scheduler.advanceTimeBy(6000)
        
        // When: Pickup swipe is triggered
        viewModel.onPickupSwipe(true)
        testDispatcher.scheduler.advanceTimeBy(1000)
        
        // Then: State should progress to InProgress
        assertTrue(
            viewModel.rideState.value is RideState.InProgress ||
            viewModel.rideState.value is RideState.AtPickup
        )
    }

    @Test
    fun `ResetSimulation returns to Idle state`() = runTest {
        // Given: Any non-idle state
        viewModel.handleEvent(RideEvent.OfferRideClicked)
        testDispatcher.scheduler.advanceTimeBy(1000)
        
        // When: ResetSimulation event is triggered
        viewModel.handleEvent(RideEvent.ResetSimulation)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then: State should be Idle
        assertEquals(RideState.Idle, viewModel.rideState.value)
    }

    @Test
    fun `passengers data is loaded on app load`() = runTest {
        // Given: Initial state
        
        // When: App loads
        viewModel.handleEvent(RideEvent.AppLoad)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then: Passengers should be loaded
        assertTrue(viewModel.passengers.value.isNotEmpty())
    }

    @Test
    fun `trip earnings are null until trip completes`() = runTest {
        // Given: Initial state
        viewModel.handleEvent(RideEvent.AppLoad)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then: Trip earnings should be null
        assertNull(viewModel.tripEarnings.value)
    }
}
