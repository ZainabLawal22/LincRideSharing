# Architecture Documentation

## 📐 Overview

LincRide follows a **Clean Architecture** approach with **MVI (Model-View-Intent)** pattern and **Event-Driven Design**. This document provides in-depth technical details about architectural decisions and implementation patterns.

## 🏛️ Architecture Layers

### 1. Presentation Layer
**Responsibility**: UI components and user interaction handling

```
presentation/
├── components/          # Reusable UI components
├── screens/            # Full-screen composables
├── theme/              # Design system (colors, typography)
├── viewmodel/          # State management and business logic orchestration
└── MainActivity.kt     # Entry point
```

**Key Patterns**:
- Unidirectional Data Flow (UDF)
- Single Source of Truth (SSOT)
- Compose state hoisting
- Event-driven updates

### 2. Domain Layer
**Responsibility**: Business logic and data models

```
domain/
├── model/              # Domain entities and events
│   ├── RideEvent       # Sealed class for all events
│   ├── RideState       # Sealed class for UI states
│   └── Models          # Data classes (Location, Passenger, etc.)
└── repository/         # Business logic implementation
    └── RideRepository  # Ride data management and simulations
```

**Key Patterns**:
- Sealed classes for type safety
- Repository pattern
- Use case encapsulation (can be extended)

### 3. Data Layer (Future)
**Responsibility**: Data sources and persistence

```
data/
├── remote/             # API services (to be implemented)
├── local/              # Local database (Room)
└── repository/         # Repository implementations
```

**Not implemented yet** - Ready for API integration

## 🔄 MVI Pattern Implementation

### Model
- **RideState**: Represents UI state
- **RideEvent**: Represents user/system intents

### View
- **RideScreen**: Observes state and renders UI
- **Components**: Stateless, data-driven composables

### Intent
- **handleEvent()**: Processes events and updates state

```kotlin
sealed class RideEvent {
    object AppLoad : RideEvent()
    object OfferRideClicked : RideEvent()
    // ... more events
}

sealed class RideState {
    object Idle : RideState()
    data class OfferingRide(val progress: Float) : RideState()
    // ... more states
}
```

## 🎯 Event-Driven Architecture

### Event Flow Diagram
```
User Action → RideEvent → ViewModel.handleEvent() → 
Repository (if needed) → State Update → UI Recomposition
```

### Event Processing
```kotlin
fun handleEvent(event: RideEvent) {
    viewModelScope.launch {
        when (event) {
            is RideEvent.OfferRideClicked -> {
                _rideState.value = RideState.OfferingRide(0f)
                startPickupAnimation()
            }
            // ... handle other events
        }
    }
}
```

### Benefits
- ✅ Testable: Easy to test state transitions
- ✅ Predictable: Clear event → state mapping
- ✅ Debuggable: Event history tracking possible
- ✅ Scalable: Easy to add new events

## 🗺️ Google Maps Integration

### Architecture
```
MapScreen (Composable)
    ↓
GoogleMap (Compose)
    ↓
MapProperties, UiSettings
    ↓
Markers, Polylines
```

### Implementation Details

**Camera Management**:
```kotlin
val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.fromLatLngZoom(center, 13f)
}

// Auto-update camera to follow car
LaunchedEffect(carPosition) {
    carPosition?.let {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 14f)
    }
}
```

**Route Animation**:
```kotlin
// Generate smooth waypoints
fun generateWaypoints(start: LatLng, end: LatLng, numPoints: Int): List<LatLng> {
    return (0..numPoints).map { i ->
        val fraction = i.toDouble() / numPoints
        LatLng(
            start.latitude + (end.latitude - start.latitude) * fraction,
            start.longitude + (end.longitude - start.longitude) * fraction
        )
    }
}

// Animate car along waypoints
simulateCarProgress().collect { progress ->
    val index = (progress * waypoints.size).toInt()
    _carPosition.value = waypoints[index]
}
```

## 🔄 State Management

### StateFlow vs LiveData
**Why StateFlow?**
- ✅ Kotlin-first API
- ✅ Better coroutine integration
- ✅ Value property for immediate access
- ✅ Composable integration with `collectAsStateWithLifecycle()`

### State Collection
```kotlin
@Composable
fun RideScreen(viewModel: RideViewModel) {
    val rideState by viewModel.rideState.collectAsStateWithLifecycle()
    
    when (rideState) {
        is RideState.Idle -> IdleUI()
        is RideState.OfferingRide -> OfferRideUI()
        // ...
    }
}
```

### State Updates
```kotlin
// Private mutable state
private val _rideState = MutableStateFlow<RideState>(RideState.Idle)

// Public immutable state
val rideState: StateFlow<RideState> = _rideState.asStateFlow()

// Update state
_rideState.value = RideState.OfferingRide(progress = 0.5f)
```

## 🎨 UI Component Architecture

### Component Hierarchy
```
RideScreen (Orchestrator)
    ├── MapScreen (Background)
    └── Overlay Layer
        ├── OfferRideBottomSheet
        ├── PickupConfirmationBottomSheet
        ├── HeadingToDestinationBottomSheet
        └── TripCompletedOverlay
```

### Component Principles
1. **Single Responsibility**: Each component has one purpose
2. **Stateless**: Components don't manage their own state
3. **Reusable**: Can be used in different contexts
4. **Testable**: Easy to preview and test

### Example Component
```kotlin
@Composable
fun OfferRideBottomSheet(
    progress: Float,              // State from ViewModel
    driver: Driver?,              // Data
    onEmergency: () -> Unit,      // Event callbacks
    modifier: Modifier = Modifier // Customization
) {
    // Pure UI implementation
}
```

## 🧪 Testing Architecture

### Test Pyramid
```
     /\
    /UI\          UI Tests (Compose Testing)
   /____\
  /      \        Integration Tests
 / Unit   \       ViewModel Tests (80%+ coverage)
/__________\
```

### ViewModel Testing Strategy
```kotlin
@Test
fun `event flow progresses through states correctly`() = runTest {
    // Given: Initial state
    
    // When: Event is triggered
    viewModel.handleEvent(RideEvent.OfferRideClicked)
    
    // Then: State transitions correctly
    assertEquals(RideState.OfferingRide(0f), viewModel.rideState.value)
}
```

### Test Tools
- **JUnit 4**: Test framework
- **MockK**: Mocking library
- **Turbine**: Flow testing
- **Coroutine Test**: Async testing

## 🔧 Dependency Injection

### Hilt Architecture
```
@HiltAndroidApp
LincRideApplication
    ↓
@InstallIn(SingletonComponent::class)
AppModule
    ↓
@HiltViewModel
RideViewModel
    ↓
@Inject RideRepository
```

### Module Setup
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRideRepository(): RideRepository = RideRepository()
}
```

### ViewModel Injection
```kotlin
@HiltViewModel
class RideViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {
    // Implementation
}
```

## 🎭 Animation Architecture

### Animation Layers
1. **Compose Animations**: fadeIn, slideIn, etc.
2. **Custom Animations**: Progress bars, car movement
3. **Map Animations**: Camera transitions

### Implementation Pattern
```kotlin
AnimatedVisibility(
    visible = showBottomSheet,
    enter = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(durationMillis = 300)
    ),
    exit = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(durationMillis = 300)
    )
) {
    BottomSheetContent()
}
```

## 📦 Package Structure Philosophy

### Why This Structure?
- **Feature-based** (future): Group by feature, not layer
- **Layer-based** (current): Clear separation of concerns
- **Scalable**: Easy to add new features/modules

### Migration Path
```
Current (Layer-based):
com.lincride/
├── domain/
├── presentation/
└── data/

Future (Feature-based):
com.lincride/
├── feature/
│   ├── ride/
│   │   ├── domain/
│   │   ├── presentation/
│   │   └── data/
│   └── profile/
└── core/
```

## 🔐 Security Considerations

### API Key Management
- ✅ Stored in `local.properties` (not committed)
- ✅ Injected via Gradle Secrets plugin
- ✅ Not hardcoded in source

### ProGuard Rules
```proguard
# Keep domain models
-keep class com.lincride.domain.model.** { *; }

# Obfuscate implementation
-keep class com.lincride.** { *; }
```

## 📈 Performance Optimizations

### Compose Optimizations
1. **Stable Classes**: Mark data classes as `@Stable`
2. **Remember**: Cache expensive computations
3. **Lazy Layouts**: Use LazyColumn for lists
4. **Derivation**: Use `derivedStateOf` for computed values

### Memory Management
```kotlin
// Proper lifecycle handling
viewModelScope.launch {
    // Automatically cancelled when ViewModel is cleared
}

// Collect with lifecycle awareness
collectAsStateWithLifecycle()
```

### Map Optimizations
- Waypoint batching (20-25 points)
- Camera animation throttling
- Marker clustering (future)

## 🔮 Future Enhancements

### Planned Improvements
1. **Multi-module**: Feature modules
2. **Clean Architecture**: Use cases layer
3. **Backend Integration**: REST API/GraphQL
4. **Offline Support**: Room database
5. **Real-time**: WebSocket updates

### Extension Points
- Add new `RideEvent` types
- Add new `RideState` types
- Inject different `RideRepository` implementations
- Add interceptors for logging/analytics

## 📚 References

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Compose Best Practices](https://developer.android.com/jetpack/compose/mental-model)
- [MVI Pattern](https://hannesdorfmann.com/android/mosby3-mvi-1/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

---

**Last Updated**: December 2024  
**Version**: 1.0.0
