package com.lincride.presentation.components



import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lincride.domain.model.Passenger
import com.lincride.presentation.theme.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Shows swipeable action for rider pickup confirmation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickupConfirmationBottomSheet(
    passenger: Passenger?,
    pickupLocation: String,
    waitingTime: String,
    onSwipe: (didShow: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
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

            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(8.dp))

            // Status text and waiting time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rider is arriving...",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF2A2A2A)
                )

                // Waiting time
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = waitingTime,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF2A2A2A)
                    )

                    Text(
                        text = "Waiting time",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        color = Color(0xFF2A2A2A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // "To Pick up" Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = TextWhite
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    // "To Pick up" label
                    Text(
                        text = "To Pick up",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF666666),
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
                                contentDescription = "Passenger",
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = passenger?.name ?: "Nneka Chukwu",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                // Verified icon
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
                                    text = passenger?.rating?.toString() ?: "4.7",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF666666),
                                    fontSize = 13.sp
                                )
                            }
                        }

                        // Message button
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)

                        ) {
                            Image(
                                painter = painterResource(id = com.lincride.R.drawable.message_icon),
                                contentDescription = "Message",
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Call button
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)

                        ) {
                            Image(
                                painter = painterResource(id = com.lincride.R.drawable.call_icon),
                                contentDescription = "Call",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pickup location with dashed line
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Dashed line with circles
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF666666), CircleShape)
                    )

                    // Dashed line
                    Column(
                        modifier = Modifier.height(30.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(6.dp)
                                    .background(Color(0xFF666666))
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Pick-up point",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF666666),
                        fontSize = 11.sp
                    )
                    Text(
                        text = pickupLocation,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            SwipeablePickupActionFigma(
                onSwipeComplete = onSwipe
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Available seats and Passengers
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
                        repeat(2) { index ->
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
                    color = Color(0xFF383838),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun SwipeablePickupActionFigma(
    onSwipeComplete: (didShow: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
           // .clip(RoundedCornerShape(28.dp))
    ) {
        // Background gradient (Red to Green)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LincRed,
                            LincGreen
                        )
                    )
                )
        )

        // "Didn't show" text (left)
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "< <", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Didn't show",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        // "Picked up" text (right)
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Picked up",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "> >", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }


        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(3000) // 3 seconds
            onSwipeComplete(true) // Auto "Picked up"
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PickupConfirmationBottomSheetPreview() {
    LincRideTheme {
        PickupConfirmationBottomSheet(
            passenger = Passenger(
                id = "1",
                name = "Nneka Chukwu",
                rating = 4.7f,
                imageUrl = null
            ),
            pickupLocation = "Ladipo Oluwole Street",
            waitingTime = "04:45",
            onSwipe = { }
        )
    }
}
