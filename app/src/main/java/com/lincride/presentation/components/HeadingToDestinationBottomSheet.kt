package com.lincride.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lincride.domain.model.Passenger
import com.lincride.presentation.theme.*

/**
 * Shows trip in progress with destination details
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadingToDestinationBottomSheet(
    progress: Float,
    passengers: List<Passenger>,
    estimatedTime: String,
    destination: String,
    modifier: Modifier = Modifier
) {
    // Animate progress
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000),
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



            Spacer(modifier = Modifier.height(16.dp))

            // Header: "Heading to" and destination
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Heading to",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = destination,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.End
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "To drop off",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF666666),
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        // Passenger avatar
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = com.lincride.R.drawable.profile_image),
                                contentDescription = "Passenger",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar with car icon
            ProgressBarWithCar(progress = animatedProgress)

            Spacer(modifier = Modifier.height(16.dp))

            // Timeline with drop-off points
            DropOffTimeline(
                passengers = passengers,
                destination = destination,
                estimatedTime = estimatedTime
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Available seats and Passengers accepted
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Available seats",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF666666),
                        fontSize = 12.sp
                    )
                    Text(
                        text = "1",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Passengers accepted",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF666666),
                        fontSize = 12.sp
                    )

                    // Passenger avatars
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        passengers.take(2).forEachIndexed { index, _ ->
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (index == 0) Color(0xFFFFB6C1)
                                        else Color(0xFF9EC0FF)
                                    )
                                    .border(2.dp, Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = com.lincride.R.drawable.profile_image),
                                    contentDescription = "Passenger ${index + 1}",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Share Ride Info button
            OutlinedButton(
                onClick = { /* Handle share */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
            ) {
                Text(
                    text = "Share Ride Info",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun ProgressBarWithCar(progress: Float) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        val maxOffsetDp = maxWidth - 32.dp

        // Gray background track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE0E0E0))
        )

        // Green progress fill
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(8.dp)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(4.dp))
                .background(LincGreen)
        )

        // Car icon
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = maxOffsetDp * progress.coerceIn(0f, 1f))
                .size(32.dp)
        ) {

            Icon(
                painter = painterResource(id = com.lincride.R.drawable.car),
                contentDescription = "car",
                modifier = Modifier.fillMaxSize(),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
private fun DropOffTimeline(
    passengers: List<Passenger>,
    destination: String,
    estimatedTime: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Starting Point
        TimelineItem(
            circleColor = Color.White,
            circleBorder = Color.Black,
            label = "Starting Point",
            location = "Ladipo Oluwole Street",
            showDashedLine = true,
            dashedLineColor = LincGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Drop-off 1
        TimelineItem(
            circleColor = LincGreen,
            circleBorder = null,
            label = "Drop-off 1",
            labelColor = LincGreen,
            location = destination,
            showDashedLine = true,
            dashedLineColor = Color(0xFFFF9500), // Orange
            avatarIndex = 0
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                thickness = 1.dp,
                color = Color(0xFFE0E0E0)
            )

            // Card overlapping the divider (positioned on the right)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 0.dp
                ) {
                    Text(
                        text = "Through Aromire Str. • $estimatedTime",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF666666),
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Drop-off 2
        TimelineItem(
            circleColor = Color(0xFFFF9500), // Orange
            circleBorder = null,
            label = "Drop-off 2",
            labelColor = Color(0xFFFF9500),
            location = destination,
            showDashedLine = true,
            dashedLineColor = Color(0xFF999999), // Gray
            avatarIndex = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Destination
        TimelineItem(
            circleColor = Color.Black,
            circleBorder = null,
            label = "Destination",
            location = destination,
            showDashedLine = false
        )
    }
}
@Composable
private fun TimelineItem(
    circleColor: Color,
    circleBorder: Color?,
    label: String,
    labelColor: Color? = null,
    location: String,
    showDashedLine: Boolean,
    dashedLineColor: Color = Color.Gray,
    avatarIndex: Int? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(24.dp)
        ) {
            // Circle
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(circleColor)
                    .then(
                        if (circleBorder != null) {
                            Modifier.border(2.dp, circleBorder, CircleShape)
                        } else Modifier
                    )
            )

            // Dashed line
            if (showDashedLine) {
                Column(
                    modifier = Modifier.height(40.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(6.dp)
                                .background(dashedLineColor)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Location info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = labelColor ?: Color(0xFF666666),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }

            // Avatar for drop-off points
            if (avatarIndex != null) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = com.lincride.R.drawable.profile_image),
                        contentDescription = "Passenger",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HeadingToDestinationBottomSheetPreview() {
    LincRideTheme {
        HeadingToDestinationBottomSheet(
            progress = 0.65f,
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
            estimatedTime = "4 min",
            destination = "Community Road"
        )
    }
}