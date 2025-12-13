package com.lincride.presentation.screens

//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.FloatingActionButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.MapProperties
//import com.google.maps.android.compose.MapType
//import com.google.maps.android.compose.MapUiSettings
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.Polyline
//import com.google.maps.android.compose.rememberCameraPositionState
//import com.lincride.presentation.theme.LincBlue
//import com.lincride.presentation.theme.LincGreen
//import com.lincride.presentation.theme.LincRideTheme
//import com.lincride.presentation.theme.TextSecondary
//
///**
// * Displays Google Map with driver location and UI controls
// */
//@Composable
//fun MapScreen(
//    carPosition: LatLng?,
//    routeWaypoints: List<LatLng>,
//    onOfferRideClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val sanFranciscoCenter = LatLng(37.7749, -122.4194)
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(sanFranciscoCenter, 13f)
//    }
//
//    // Update camera to follow car if available
//    LaunchedEffect(carPosition) {
//        carPosition?.let {
//            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 14f)
//        }
//    }
//
//    Box(modifier = modifier.fillMaxSize()) {
//        // Google Map
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            cameraPositionState = cameraPositionState,
//            properties = MapProperties(
//                isMyLocationEnabled = false,
//                mapType = MapType.NORMAL
//            ),
//            uiSettings = MapUiSettings(
//                zoomControlsEnabled = false,
//                myLocationButtonEnabled = false,
//                mapToolbarEnabled = false
//               // compassEnabled = false
//            )
//        ) {
//            // Draw route polyline if waypoints are available
//            if (routeWaypoints.isNotEmpty()) {
//                Polyline(
//                    points = routeWaypoints,
//                    color = LincBlue,
//                    width = 8f
//                )
//            }
//
//            // Car marker
//            carPosition?.let { position ->
//                Marker(
//                    state = MarkerState(position = position),
//                    title = "Your Location"
//                )
//            }
//        }
//
//        // Active campaign banner (Figma position - above bottom sheet)
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 300.dp)
//                .height(70.dp),
//            color = LincGreen,
//            shape = RoundedCornerShape(14.dp),
//            shadowElevation = 3.dp
//        ) {
//            Row(
//                modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 12.dp)
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    painter = painterResource(id = com.lincride.R.drawable.icon),
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(10.dp))
//                Text(
//                    text = "1 Active campaign",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 15.sp,
//                    color = Color.Black
//                )
//            }
//        }
//
//        // Bottom navigation and offer ride section
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter)
//        ) {
//            // Offer Ride Section with drag handle
//            Surface(
//                modifier = Modifier.fillMaxWidth(),
//                color = Color.White,
//                shadowElevation = 8.dp,
//                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
//            ) {
//                Column(
//                    modifier = Modifier.padding(20.dp)
//                ) {
//                    // Drag handle
//                    Box(
//                        modifier = Modifier
//                            .width(80.dp)
//                            .height(5.dp)
//                            .clip(RoundedCornerShape(3.dp))
//                            .background(Color(0xFFB0B0B0))
//                            .align(Alignment.CenterHorizontally)
//                    )
//
//                    Spacer(modifier = Modifier.height(20.dp))
//// todo: center the text Choose your ride mode
//                    Text(
//                        text = "Choose your ride mode",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(bottom = 16.dp)
//                    )
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        // Join a Ride option - with group profile pictures
//                        OutlinedButton(
//                            onClick = { /* Handle join ride */ },
//                            modifier = Modifier
//                                .weight(1f)
//                                .height(75.dp),
//                            shape = RoundedCornerShape(16.dp),
//                            border = ButtonDefaults.outlinedButtonBorder
//                        ) {
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.Start,
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                // Multiple profile pictures (group icon)
//                                Box(
//                                    modifier = Modifier.size(48.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    // Three overlapping circles representing group
//                                    Box(
//                                        modifier = Modifier
//                                            .size(32.dp)
//                                            .offset(x = (-8).dp)
//                                            .clip(CircleShape)
//                                            .background(Color(0xFFFFE5E5)),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//
//                                        // todo: change to group icon to match the figma design, and the icon is duplicate and text is
//                                        Icon(
//                                            painter = painterResource(id = com.lincride.R.drawable.frame_join_a_ride),
//                                            contentDescription = null,
//                                         //   tint = Color(0xFFFF6B6B),
//                                            modifier = Modifier.size(18.dp)
//                                        )
//                                    }
//                                    Box(
//                                        modifier = Modifier
//                                            .size(32.dp)
//                                            .offset(x = 8.dp)
//                                            .clip(CircleShape)
//                                            .background(Color(0xFFE3F2FD)),
//                                        contentAlignment = Alignment.Center
//                                    ) {
//                                        Icon(
//                                            painter = painterResource(id = com.lincride.R.drawable.frame_join_a_ride),
//                                            contentDescription = null,
//                                            //tint = null,
//                                            modifier = Modifier.size(18.dp)
//                                        )
//                                    }
//                                }
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Column(horizontalAlignment = Alignment.Start) {
//                                    Text(
//                                        text = "Join a Ride",
//                                        style = MaterialTheme.typography.bodyMedium,
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = 15.sp,
//                                        color = Color.Black
//                                    )
//                                    Text(
//                                        text = "Book your seat",
//                                        style = MaterialTheme.typography.bodySmall,
//                                        fontSize = 13.sp,
//                                        color = TextSecondary
//                                    )
//                                }
//                            }
//                        }
//
//                        // Offer Ride option - with single profile picture
//                        Button(
//                            onClick = onOfferRideClick,
//                            modifier = Modifier
//                                .weight(1f)
//                                .height(75.dp),
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = LincBlue
//                            ),
//                            shape = RoundedCornerShape(16.dp)
//                        ) {
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.Start,
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                // Single profile picture
//                                Box(
//                                    modifier = Modifier
//                                        .size(48.dp)
//                                        .clip(CircleShape)
//                                        .background(Color.White.copy(alpha = 0.3f)),
//                                    contentAlignment = Alignment.Center
//                                ) {
//
//                                    // todo : change the icon to match the figma design
//                                    Icon(
//                                        painter = painterResource(id = com.lincride.R.drawable.frame_offer_a_ride),
//                                        contentDescription = null,
//                                       // tint = Color.White,
//                                        modifier = Modifier.size(28.dp)
//                                    )
//                                }
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Column(horizontalAlignment = Alignment.Start) {
//                                    Text(
//                                        text = "Offer Ride",
//                                        style = MaterialTheme.typography.bodyMedium,
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = 15.sp
//                                    )
//                                    Text(
//                                        text = "Share your trip",
//                                        style = MaterialTheme.typography.bodySmall,
//                                        fontSize = 13.sp
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Where to? button with location pin icon
//                    OutlinedButton(
//                        onClick = { /* Handle where to */ },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Start
//                        ) {
//                            // todo: change to location pin icon
//                            Icon(
//                                painter = painterResource(id = com.lincride.R.drawable.frame_location),
//                                contentDescription = null,
//                                modifier = Modifier.size(24.dp),
//                                tint = TextSecondary
//                            )
//                            Spacer(modifier = Modifier.width(12.dp))
//                            Text(
//                                text = "Where to?",
//                                style = MaterialTheme.typography.bodyMedium,
//                                fontSize = 16.sp,
//                                color = TextSecondary
//                            )
//                        }
//                    }
//                }
//            }
//
//            // Bottom Navigation
//            BottomNavigation(modifier = Modifier.fillMaxWidth())
//        }
//
//        // Compass/Location button (Figma position)
//        FloatingActionButton(
//            onClick = { /* Center on location */ },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(end = 16.dp, bottom = 320.dp),
//            containerColor = Color.White,
//            shape = CircleShape,
//            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
//        ) {
//            // todo: change this icon position, move it to the map, and also the icon to match the figma
//            Icon(
//                painter = painterResource(id = com.lincride.R.drawable.gps),
//                contentDescription = "Center location",
//                tint = Color.Black,
//                modifier = Modifier.size(24.dp)
//            )
//        }
//    }
//}
//
//@Composable
//private fun BottomNavigation(modifier: Modifier = Modifier) {
//    Surface(
//        modifier = modifier,
//        color = Color.White,
//        shadowElevation = 0.dp
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 12.dp, horizontal = 8.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            BottomNavItem(
//                icon = painterResource(id = com.lincride.R.drawable.home_icon),
//                label = "Home",
//                selected = true,
//                isProfile = false
//            )
//            BottomNavItem(
//                icon = painterResource(id = com.lincride.R.drawable.history_icon),
//                label = "History",
//                selected = false,
//                isProfile = false
//            )
//            BottomNavItem(
//                icon = painterResource(id = com.lincride.R.drawable.profile_image),
//                label = "Profile",
//                selected = false,
//                isProfile = true
//            )
//        }
//    }
//}
//
//@Composable
//private fun BottomNavItem(
//    icon: Painter,
//    label: String,
//    selected: Boolean,
//    isProfile: Boolean
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(horizontal = 12.dp)
//    ) {
//        if (isProfile) {
//            // Profile picture for Account/Profile tab
//            Box(
//                modifier = Modifier
//                    .size(28.dp)
//                    .clip(CircleShape)
//                    .background(if (selected) LincBlue else Color(0xFFFFB6C1)),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = icon,
//                    contentDescription = label,
//                    tint = Color.White,
//                    modifier = Modifier.size(18.dp)
//                )
//            }
//        } else {
//            Icon(
//                painter = icon,
//                contentDescription = label,
//                tint = if (selected) Color.Black else TextSecondary,
//                modifier = Modifier.size(28.dp)
//            )
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = label,
//            style = MaterialTheme.typography.labelSmall,
//            fontSize = 12.sp,
//            color = if (selected) Color.Black else TextSecondary,
//            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
//        )
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun MapScreenPreview() {
//    LincRideTheme {
//        MapScreen(
//            carPosition = LatLng(37.7749, -122.4194),
//            routeWaypoints = listOf(
//                LatLng(37.7749, -122.4194),
//                LatLng(37.7849, -122.4094),
//                LatLng(37.7949, -122.3994)
//            ),
//            onOfferRideClick = { }
//        )
//    }
//}

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.lincride.presentation.theme.LincBlue
import com.lincride.presentation.theme.LincDarkGreen
import com.lincride.presentation.theme.LincGreen
import com.lincride.presentation.theme.LincLightGray
import com.lincride.presentation.theme.LincRideTheme
import com.lincride.presentation.theme.TextSecondary

/**
 * Displays Google Map with driver location and UI controls
 */
@Composable
fun MapScreen(
    carPosition: LatLng?,
    routeWaypoints: List<LatLng>,
    onOfferRideClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val lagosCenter = LatLng(6.5244, 3.3792)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lagosCenter, 14f)
    }

    // Update camera to follow car if available
    LaunchedEffect(carPosition) {
        carPosition?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Google Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = false,
                mapType = MapType.NORMAL
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false,
                mapToolbarEnabled = false,
                compassEnabled = false
            )
        ) {
            // Draw route polyline if waypoints are available
            if (routeWaypoints.isNotEmpty()) {
                Polyline(
                    points = routeWaypoints,
                    color = LincBlue,
                    width = 8f
                )
            }

            // Car marker
            carPosition?.let { position ->
                Marker(
                    state = MarkerState(position = position),
                    title = "Your Location"
                )
            }
        }


        Surface(
            modifier = Modifier
                .fillMaxWidth()
              //  .wrapContentHeight()
                .align(Alignment.BottomCenter)
            // .padding(bottom = 300.dp),
          .offset(y = (-285).dp),
            color = LincGreen,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Image(
                    painter = painterResource(id = com.lincride.R.drawable.icon),
                    contentDescription = null,
                   // modifier = Modifier.size(24.dp)
                            modifier = Modifier
                           // .fillMaxWidth()
                        .size(24.dp)
                     //   .background(LincGreen)
                      //  .padding(16.dp)
                     //.align(Alignment.CenterStart)
                       .offset(y = (-18).dp)
                )

                Spacer(modifier = Modifier.width(1.dp))
                Text(
                    text = "1 Active campaign",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LincDarkGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LincGreen)
                        .padding(16.dp)
                       // .align(Alignment.CenterStart)
                        .offset(y = (-18).dp)
                )
            }
        }
        // Bottom navigation and offer ride section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(5.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(LincLightGray)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Choose your ride mode",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = LincDarkGreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Join a Ride option
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 51.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = null,
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Group profile pictures
                                Box(
                                    modifier = Modifier.size(50.dp),
                                    contentAlignment = Alignment.Center
                                ) {

                                    Image(
                                        painter = painterResource(id = com.lincride.R.drawable.frame_join_a_ride),
                                        contentDescription = null,
                                        modifier = Modifier.size(50.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(
                                        text = "Join a Ride",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Book your seat",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 13.sp,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }

                        // Offer Ride option
                        Button(
                            onClick = onOfferRideClick,
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 51.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LincBlue
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Single profile picture
                                Box(
                                    modifier = Modifier.size(50.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = com.lincride.R.drawable.frame_offer_a_ride),
                                        contentDescription = null,
                                        modifier = Modifier.size(50.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(horizontalAlignment = Alignment.Start) {
                                    Text(
                                        text = "Offer Ride",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Share your trip",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 13.sp,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Where to? search
                    OutlinedButton(
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = null,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFF8F8F8)
                        )

                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
//                            Icon(
//                                painter = painterResource(id = com.lincride.R.drawable.frame_location),
//                                contentDescription = null,
//                                modifier = Modifier.size(24.dp),
//                                tint = TextSecondary
//                            )

                            Image(
                                painter = painterResource(id = com.lincride.R.drawable.frame_location),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Where to?",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 16.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }

            // Bottom Navigation
            BottomNavigation(modifier = Modifier.fillMaxWidth())
        }

        // Compass/Navigation button
        FloatingActionButton(
            onClick = { /* Center on location */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 420.dp),
            containerColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
        ) {
//            Icon(
//                painter = painterResource(id = com.lincride.R.drawable.gps),
//                contentDescription = "Center location",
//                tint = Color.Black,
//                modifier = Modifier.size(24.dp)
//            )

            Image(
                painter = painterResource(id = com.lincride.R.drawable.gps),
                contentDescription = "Center location",
                modifier = Modifier.size(50.dp)
            )


        }
    }
}

@Composable
private fun BottomNavigation(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                icon = painterResource(id = com.lincride.R.drawable.home_icon),
                label = "Home",
                selected = true,
                isProfile = false
            )
            BottomNavItem(
                icon = painterResource(id = com.lincride.R.drawable.history_icon),
                label = "History",
                selected = false,
                isProfile = false
            )
            BottomNavItem(
                icon = painterResource(id = com.lincride.R.drawable.profile_image),
                label = "Profile",
                selected = false,
                isProfile = true
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: Painter,
    label: String,
    selected: Boolean,
    isProfile: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        if (isProfile) {
            // Profile picture
            Box(
                modifier = Modifier
                    .size(32.dp) // Larger
                    .clip(CircleShape)
                    .background(if (selected) LincBlue else Color(0xFFFFB6C1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = label,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(32.dp)
                )
            }
        } else {
            Icon(
                painter = icon,
                contentDescription = label,
                tint = if (selected) Color.Black else Color(0xFF9E9E9E),
                modifier = Modifier.size(30.dp) // Larger icons
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 11.sp,
            color = if (selected) Color.Black else Color(0xFF9E9E9E),
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MapScreenPreview() {
    LincRideTheme {
        MapScreen(
            carPosition = LatLng(6.5244, 3.3792), // Lagos coordinates
            routeWaypoints = listOf(
                LatLng(6.5244, 3.3792),
                LatLng(6.5344, 3.3892),
                LatLng(6.5444, 3.3992)
            ),
            onOfferRideClick = { }
        )
    }
}
