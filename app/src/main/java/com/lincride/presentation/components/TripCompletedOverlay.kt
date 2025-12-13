//package com.lincride.presentation.components
//
//import androidx.compose.animation.*
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.lincride.domain.model.Passenger
//import com.lincride.domain.model.TripEarnings
//import com.lincride.presentation.theme.*
//
///**
// * Shows trip completion with earnings breakdown
// */
//@Composable
//fun TripCompletedOverlay(
//    earnings: TripEarnings?,
//    passengers: List<Passenger>,
//    onDismiss: () -> Unit,
//    onNewTrip: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(CompletedBackground),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Close button
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                IconButton(
//                    onClick = onDismiss,
//                    modifier = Modifier
//                        .size(40.dp)
//                        .clip(CircleShape)
//                        .background(Color.White.copy(alpha = 0.2f))
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Close",
//                        tint = Color.White
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(40.dp))
//
//            // Success icon and message
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//                    .background(Color.White.copy(alpha = 0.2f)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "✓",
//                    fontSize = 60.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "Trip Complete! Thank You.",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = "Another successful trip, well done!",
//                style = MaterialTheme.typography.bodyLarge,
//                color = Color.White.copy(alpha = 0.9f),
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            // Earnings card
//            Surface(
//                modifier = Modifier.fillMaxWidth(),
//                color = Color.White,
//                shape = RoundedCornerShape(20.dp),
//                shadowElevation = 4.dp
//            ) {
//                Column(
//                    modifier = Modifier.padding(24.dp)
//                ) {
//                    // Distance and riders
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        // Distance
//                        Box(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(12.dp))
//                                .background(CompletedGreen.copy(alpha = 0.1f))
//                                .padding(horizontal = 16.dp, vertical = 8.dp)
//                        ) {
//                            Text(
//                                text = "🚗 ${earnings?.distance ?: 0.86} kg",
//                                style = MaterialTheme.typography.bodyMedium,
//                                fontWeight = FontWeight.SemiBold,
//                                color = CompletedGreen
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.width(12.dp))
//
//                        // Helped riders
//                        Box(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(12.dp))
//                                .background(CompletedGreen.copy(alpha = 0.1f))
//                                .padding(horizontal = 16.dp, vertical = 8.dp)
//                        ) {
//                            Text(
//                                text = "🌟 +${earnings?.helpedRiders ?: 4} your user are preferred",
//                                style = MaterialTheme.typography.bodyMedium,
//                                fontWeight = FontWeight.SemiBold,
//                                color = CompletedGreen
//                            )
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    Text(
//                        text = "You helped ${earnings?.helpedRiders ?: 4} riders get to their destinations.",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = TextSecondary,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    // Rate passengers section
//                    Text(
//                        text = "Rate your passengers",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    passengers.forEach { passenger ->
//                        PassengerRatingItem(passenger = passenger)
//                        Spacer(modifier = Modifier.height(12.dp))
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    Divider(color = DividerColor)
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    // Earnings breakdown
//                    Text(
//                        text = "Earnings for This Trip",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    EarningsRow(
//                        label = "Net Earnings",
//                        amount = earnings?.netEarnings ?: 6600.0
//                    )
//                    EarningsRow(
//                        label = "Bonus",
//                        amount = earnings?.bonus ?: 500.0
//                    )
//                    EarningsRow(
//                        label = "Linc Commission",
//                        amount = earnings?.commission ?: 500.0,
//                        isNegative = true
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Divider(color = DividerColor)
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Total
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Total",
//                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//
//                        val total = (earnings?.netEarnings ?: 6600.0) +
//                                   (earnings?.bonus ?: 500.0) -
//                                   (earnings?.commission ?: 500.0)
//                        Text(
//                            text = "₦${String.format("%.2f", total)}",
//                            style = MaterialTheme.typography.headlineSmall,
//                            fontWeight = FontWeight.Bold,
//                            color = CompletedGreen
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    // Action buttons
//                    Button(
//                        onClick = onNewTrip,
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Black
//                        ),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Text(
//                            text = "New Trip",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.SemiBold,
//                            modifier = Modifier.padding(vertical = 8.dp)
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    OutlinedButton(
//                        onClick = { /* View earnings history */ },
//                        modifier = Modifier.fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp)
//                    ) {
//                        Text(
//                            text = "Earnings History",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.SemiBold,
//                            modifier = Modifier.padding(vertical = 8.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun PassengerRatingItem(
//    passenger: Passenger,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape)
//                    .background(PassengerButtonGreen),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = passenger.name.first().toString(),
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//            }
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            Column {
//                Text(
//                    text = passenger.name,
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Medium
//                )
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = "★",
//                        color = RatingYellow,
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = passenger.rating.toString(),
//                        style = MaterialTheme.typography.bodySmall,
//                        color = TextSecondary
//                    )
//                }
//            }
//        }
//
//        Button(
//            onClick = { /* Handle rating */ },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = SurfaceLight
//            ),
//            shape = RoundedCornerShape(8.dp),
//            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//        ) {
//            Text(
//                text = "Rate now",
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.SemiBold,
//                color = TextPrimary
//            )
//        }
//    }
//}
//
//@Composable
//private fun EarningsRow(
//    label: String,
//    amount: Double,
//    isNegative: Boolean = false,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            text = label,
//            style = MaterialTheme.typography.bodyLarge,
//            color = TextSecondary
//        )
//
//        Text(
//            text = "${if (isNegative) "-" else ""}₦${String.format("%.2f", amount)}",
//            style = MaterialTheme.typography.bodyLarge,
//            fontWeight = FontWeight.SemiBold,
//            color = if (isNegative) ErrorRed else TextPrimary
//        )
//    }
//}
//
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////private fun TripCompletedOverlayPreview() {
////    LincRideTheme {
////        TripCompletedOverlay(
////            earnings = TripEarnings(
////                distance = 0.86,
////                helpedRiders = 4,
////                netEarnings = 6600.0,
////                bonus = 500.0,
////                commission = 500.0
////            ),
////            passengers = listOf(
////                Passenger(
////                    id = "1",
////                    name = "Jane Smith",
////                    rating = 4.9f,
////                    imageUrl = null
////                ),
////                Passenger(
////                    id = "2",
////                    name = "Mike Johnson",
////                    rating = 4.7f,
////                    imageUrl = null
////                ),
////                Passenger(
////                    id = "3",
////                    name = "Sarah Williams",
////                    rating = 5.0f,
////                    imageUrl = null
////                )
////            ),
////            onDismiss = { },
////            onNewTrip = { }
////        )
////    }
////}
//
//@Preview(showBackground = true, showSystemUi = true, name = "High Earnings")
//@Composable
//private fun TripCompletedHighEarningsPreview() {
//    LincRideTheme {
//        TripCompletedOverlay(
//            earnings = TripEarnings(
//                distance = 12.5,
//                helpedRiders = 6,
//                netEarnings = 15000.0,
//                bonus = 2000.0,
//                commission = 1500.0
//            ),
//            passengers = listOf(
//                Passenger(
//                    id = "1",
//                    name = "David Chen",
//                    rating = 4.8f,
//                    imageUrl = null
//                ),
//                Passenger(
//                    id = "2",
//                    name = "Emma Wilson",
//                    rating = 4.6f,
//                    imageUrl = null
//                )
//            ),
//            onDismiss = { },
//            onNewTrip = { }
//        )
//    }
//}
//
////@Preview(showBackground = true, showSystemUi = true, name = "Single Passenger")
////@Composable
////private fun TripCompletedSinglePassengerPreview() {
////    LincRideTheme {
////        TripCompletedOverlay(
////            earnings = TripEarnings(
////                distance = 3.2,
////                helpedRiders = 1,
////                netEarnings = 2500.0,
////                bonus = 200.0,
////                commission = 250.0
////            ),
////            passengers = listOf(
////                Passenger(
////                    id = "1",
////                    name = "Alex Thompson",
////                    rating = 5.0f,
////                    imageUrl = null
////                )
////            ),
////            onDismiss = { },
////            onNewTrip = { }
////        )
////    }
////}


package com.lincride.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Figma Design: 14.7.3 - TRIP ENDED
 * Shows trip completion with CO2 savings and earnings breakdown
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
                        Color(0xFF2D8B5F), // Dark green top
                        Color(0xFF4CAF7D)  // Lighter green bottom
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Close button (LEFT side)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
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

            // Carbon Emission info (CENTERED)
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

            // White card with all content
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // "You helped X riders" message
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

                    // Earnings breakdown (CENTERED)
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
                                fontSize = 15.sp
                            )
                        }

                        // New Trip button (filled black)
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
        // Background circle (light track)
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.15f))
        )

        // Partial progress arc - simulating with a rotated box
        // In production, use Canvas to draw actual arc
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .border(8.dp, Color.Transparent, CircleShape)
        )

        // Simple circular border showing partial progress
        // You can use Canvas with drawArc for accurate progress
        androidx.compose.foundation.Canvas(
            modifier = Modifier.size(180.dp)
        ) {
            // Draw background arc (full circle)
            drawCircle(
                color = Color.White.copy(alpha = 0.15f),
                radius = size.minDimension / 2,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8.dp.toPx())
            )

            // Draw progress arc (partial - about 60% progress)
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
                color = Color.White.copy(alpha = 0.8f),
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
                color = Color.White.copy(alpha = 0.7f),
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
                // TODO: Replace with actual passenger image
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
                fontSize = 14.sp
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
