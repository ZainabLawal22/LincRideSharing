package com.lincride.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lincride.domain.model.Passenger
import com.lincride.domain.model.TripEarnings
import com.lincride.presentation.theme.*

/**
 * Shows trip completion with earnings breakdown
 */
@Composable
fun TripCompletedOverlay(
    earnings: TripEarnings?,
    passengers: List<Passenger>,
    onDismiss: () -> Unit,
    onNewTrip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2D8B5F),
                        Color(0xFF4CAF7D)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CO2 Circular Badge
            CO2CircularBadge(kg = earnings?.distance ?: 0.86)

            Spacer(modifier = Modifier.height(32.dp))

            // Success message
            Text(
                text = "Trip Complete! Thank You.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Another successful trip, well done!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "☀️", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Carbon Emission Avoided: ~1.2 km private car equivalent",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {

                    Text(
                        text = "You helped ${passengers.size} riders get to their destinations.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF4CAF7D),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Rate your passengers
                    Text(
                        text = "Rate your passengers",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Passenger rating items
                    passengers.forEach { passenger ->
                        PassengerRatingItemFigma(passenger = passenger)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Earnings breakdown
                    Text(
                        text = "Earnings for This Trip",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    EarningsRow(
                        label = "Net Earnings",
                        amount = earnings?.netEarnings ?: 6500.0
                    )
                    EarningsRow(
                        label = "Bonus",
                        amount = earnings?.bonus ?: 500.0
                    )
                    EarningsRow(
                        label = "Linc Commission",
                        amount = earnings?.commission ?: 500.0
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action buttons (side by side)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Earnings History button (outlined)
                        OutlinedButton(
                            onClick = { /* View earnings history */ },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            shape = RoundedCornerShape(26.dp),
                            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
                        ) {
                            Text(
                                text = "Earnings History",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = LincBlack
                            )
                        }

                        // New Trip button
                        Button(
                            onClick = onNewTrip,
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            ),
                            shape = RoundedCornerShape(26.dp)
                        ) {
                            Text(
                                text = "New Trip",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CO2CircularBadge(kg: Double) {
    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
        )


        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .border(8.dp, Color.Transparent, CircleShape)
        )


        androidx.compose.foundation.Canvas(
            modifier = Modifier.size(180.dp)
        ) {

            drawCircle(
                color = Color.White.copy(alpha = 0.15f),
                radius = size.minDimension / 2,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8.dp.toPx())
            )


            drawArc(
                color = Color(0xFF4CAF7D),
                startAngle = -90f,
                sweepAngle = 216f, // 60% of 360 degrees
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 8.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            )
        }

        // Inner content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "CO₂",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF29E892),
                fontSize = 14.sp
            )
            Text(
                text = "${String.format("%.2f", kg)} kg",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 32.sp
            )
            Text(
                text = "So far this month",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF29E892),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun PassengerRatingItemFigma(
    passenger: Passenger,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // Profile image
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = com.lincride.R.drawable.profile_image),
                    contentDescription = passenger.name,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = passenger.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    // Verified badge
                    Image(
                        painter = painterResource(id = com.lincride.R.drawable.verify),
                        contentDescription = "Verified",
                        modifier = Modifier.size(16.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = com.lincride.R.drawable.range_icon),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = Color(0xFF666666)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Pick-up Point: Ladipo Oluwole Street",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF666666),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Rate now button
        OutlinedButton(
            onClick = { /* Handle rating */ },
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
        ) {
            Text(
                text = "Rate now",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LincBlack,
            )
        }
    }
}

@Composable
private fun EarningsRow(
    label: String,
    amount: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF666666),
            fontSize = 15.sp
        )

        Text(
            text = "₦${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TripCompletedOverlayPreview() {
    LincRideTheme {
        TripCompletedOverlay(
            earnings = TripEarnings(
                distance = 0.86,
                helpedRiders = 4,
                netEarnings = 6500.0,
                bonus = 500.0,
                commission = 500.0
            ),
            passengers = listOf(
                Passenger(
                    id = "1",
                    name = "Wade Warren",
                    rating = 4.9f,
                    imageUrl = null
                ),
                Passenger(
                    id = "2",
                    name = "Brooklyn Simmons",
                    rating = 4.7f,
                    imageUrl = null
                )
            ),
            onDismiss = { },
            onNewTrip = { }
        )
    }
}
