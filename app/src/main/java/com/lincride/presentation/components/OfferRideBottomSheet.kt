package com.lincride.presentation.components

import android.R.attr.y
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lincride.domain.model.Driver
import com.lincride.domain.model.Passenger
import com.lincride.presentation.theme.LincBlack
import com.lincride.presentation.theme.LincBlue
import com.lincride.presentation.theme.LincGreen
import com.lincride.presentation.theme.LincLightBlue
import com.lincride.presentation.theme.LincLightGray
import com.lincride.presentation.theme.LincRideTheme

/**
 * Shows driver en route to pickup location with progress indicator
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferRideBottomSheet(
    progress: Float,
    driver: Driver?,
    passengers: List<Passenger>,
    estimatedTime: String,
    pickupLocation: String,
    modifier: Modifier = Modifier
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "progress"
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        shadowElevation = 16.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Drag handle
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(LincLightGray)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Get to pickup..",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Surface(
                    color = Color(0xFFEAF1FF),
                    shape = RoundedCornerShape(36.dp),
                    modifier = Modifier.wrapContentSize()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 6.dp,
                            end = 8.dp,
                            bottom = 6.dp
                        )
                    ) {
                        Image(
                            painter = painterResource(id = com.lincride.R.drawable.clock),
                            contentDescription = "clock icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "$estimatedTime away",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2A2A2A),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            ProgressBarWithCar(
                progress = animatedProgress
            )

            Spacer(modifier = Modifier.height(8.dp))

            DriverCard(
                driver = driver,
                pickupLocation = pickupLocation,
                estimatedTime = estimatedTime
            )

            Spacer(modifier = Modifier.height(8.dp))

            SeatsAndPassengers(passengers = passengers)

            Spacer(modifier = Modifier.height(20.dp))

            // Share Ride Info button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .width(240.dp)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Text(
                        text = "Share Ride Info",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressBarWithCar(
    progress: Float
) {

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "progress"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        val maxWidth = maxWidth

        // Background track (gray)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF0F0F0))
        )


        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(maxWidth * (1.2f - animatedProgress))
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF9EC0FF),
                            Color(0xFF1F53B5)
                        )
                    )
                )
        )


        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = -(maxWidth - 32.dp) * animatedProgress)
                .size(32.dp)
        ) {
            Icon(
                painter = painterResource(id = com.lincride.R.drawable.car),
                contentDescription = "car",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Unspecified
            )
        }


        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(15.dp)
                .clip(CircleShape)
                .background(Color(0xFF2C75FF))
                .border(4.dp, Color.White, CircleShape)
        )
    }
}


@Composable
private fun DriverCard(
    driver: Driver?,
    pickupLocation: String,
    estimatedTime: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, LincLightBlue, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
               // .padding(12.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ){
            // "To Pick" label
            Text(
                text = "To Pick",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF2A2A2A),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                            painter = painterResource(id = com.lincride.R.drawable.avatar),
                            contentDescription = "Verified",
                            modifier = Modifier.size(48.dp)
                        )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = driver?.name ?: "Darrell Steward",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = LincBlack
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Image(
                            painter = painterResource(id = com.lincride.R.drawable.verify),
                            contentDescription = "Verified",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "⭐", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = driver?.rating?.toString() ?: "4.7",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF656565),
                            fontSize = 13.sp
                        )
                    }
                }

                Image(
                    painter = painterResource(id = com.lincride.R.drawable.message_icon),
                    contentDescription = "message",
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    painter = painterResource(id = com.lincride.R.drawable.call_icon),
                    contentDescription = "call",
                    modifier = Modifier.size(36.dp)
                )
            }}

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFEAFFF6),
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                       // .size(40.dp)
                        .offset(y = 4.dp)
                        .clip(CircleShape)
                ){
                    Image(
                        painter = painterResource(id = com.lincride.R.drawable.range_icon),
                        contentDescription = "range",
                        modifier = Modifier.width(20.dp)
                        .height(40.dp)

                    )
                }



                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pick-up point",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF656565),
                        fontSize = 11.sp
                    )
                    Text(
                        text = pickupLocation,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color(0xFF383838),
                        lineHeight = 18.sp
                    )
                }

                Surface(
                    color = Color(0xFFBEFFE2),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "ETA • $estimatedTime",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF2A2A2A),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }}
}

@Composable
private fun SeatsAndPassengers(passengers: List<Passenger>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween, // Spread items across
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Available seats - label and number together
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Available seats",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF656565),
                fontSize = 12.sp
            )
            Text(
                text = "2",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF2A2A2A),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }

        // Passengers accepted - label and images together
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Passengers accepted",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF656565),
                fontSize = 12.sp
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                repeat(3) { index ->

                    val scale by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                        label = "passengerScale$index"
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(
                                when(index) {
                                    0 -> Color(0xFFFFB6C1)
                                    1 -> Color(0xFF9EC0FF)
                                    else -> LincLightGray
                                }
                            )
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {

                        when(index) {
                            0 -> {

                                Image(
                                    painter = painterResource(id = com.lincride.R.drawable.profile_image), // Or profile_image_1
                                    contentDescription = "Passenger 1",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            1 -> {

                                Image(
                                    painter = painterResource(id = com.lincride.R.drawable.avatar),
                                    contentDescription = "Passenger 2",
                                    modifier = Modifier.size(32.dp)
                                )
                            }}
                        // Third one is just an empty circle (no content inside)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OfferRideBottomSheetPreview() {
    LincRideTheme {
        OfferRideBottomSheet(
            progress = 0.6f,
            driver = Driver(
                id = "1",
                name = "Darrell Steward",
                rating = 4.7f,
                imageUrl = null
            ),
            passengers = listOf(
                Passenger(
                    id = "1",
                    name = "Jane Smith",
                    rating = 4.9f,
                    imageUrl = null
                ),
                Passenger(
                    id = "2",
                    name = "Mike Johnson",
                    rating = 4.7f,
                    imageUrl = null
                )
            ),
            estimatedTime = "4 mins",
            pickupLocation = "Ladipo Oluwole Street"
        )
    }
}
