package com.example.reminderme.Veiw.MainScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*


@SuppressLint("UnusedContentLambdaTargetStateParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OptimizedCalendar( onDateSelected: (String) -> Unit) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var isForward by remember { mutableStateOf(true) }

    var selectedDay by remember { mutableStateOf<Int?>(null) } // 👈

    val rows = remember(currentMonth) {
        val firstDow = currentMonth.atDay(1).dayOfWeek
        val leading = firstDow.value % 7
        val daysInMonth = currentMonth.lengthOfMonth()
        val totalCells = ((leading + daysInMonth + 6) / 7) * 7
        val cells = List(totalCells) { index ->
            val day = index - leading + 1
            if (index < leading || day > daysInMonth) null else day
        }
        cells.chunked(7)
    }

    val rowCount = rows.size
    val animatedHeight by animateDpAsState(
        targetValue = if (rowCount == 6) 399.dp else 365.dp,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "CardHeightAnim"
    )
    val cardOffsetY = remember { Animatable(50f) }
    val cardAlphaY = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        // Run both at same time
        launch {
            cardOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
        }

        launch {
            cardAlphaY.animateTo(1f, animationSpec = tween(600))
        }
    }
        Box(
            modifier = Modifier.height(animatedHeight).offset(y = cardOffsetY.value.dp)
                .alpha(cardAlphaY.value)

                .dropShadow(
                    shape = RoundedCornerShape(20),
                    shadow = Shadow(
                        radius = 6.dp,
                        color = Color(0x0F494949),
                        spread = 1.dp,
                        offset = DpOffset(0.dp, 1.dp)

                    )
                )
                .background(Color.White, shape = RoundedCornerShape(20.dp))

        ) {
            Column(
                modifier = Modifier.padding(22.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        currentMonth = currentMonth.minusMonths(1)
                        isForward = false
                    }) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(24.dp)) // 👈 add this line
                                .background(Color(0xFFC6CFE5))
                                .background(Color(0xFFC6CFE5), shape = RoundedCornerShape(24)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ChevronLeft,
                                contentDescription = null,
                                tint = Color(0xFF4A00E0)
                            )
                        }
                    }

                    Text(
                        text = "${
                            currentMonth.month.getDisplayName(
                                TextStyle.FULL,
                                Locale.getDefault()
                            )
                        } ${currentMonth.year}",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.Black.copy(alpha = .7f)
                    )

                    IconButton(onClick = {
                        currentMonth = currentMonth.plusMonths(1)
                        isForward = true
                    }) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(24.dp)) // 👈 add this line
                                .background(Color(0xFFC6CFE5))
                                .background(Color(0xFFC6CFE5), shape = RoundedCornerShape(24)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = Color(0xFF4A00E0)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Week headers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black.copy(alpha = .7f)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // 👇 Smooth single transition for month change
                AnimatedContent(
                    targetState = currentMonth,
                    transitionSpec = {
                        if (isForward) {
                            slideInHorizontally(
                                initialOffsetX = { it / 2 },
                                animationSpec = tween(250, easing = FastOutSlowInEasing)
                            ) + fadeIn() with
                                    slideOutHorizontally(
                                        targetOffsetX = { -it / 2 },
                                        animationSpec = tween(250)
                                    ) + fadeOut()
                        } else {
                            slideInHorizontally(
                                initialOffsetX = { -it / 2 },
                                animationSpec = tween(250, easing = FastOutSlowInEasing)
                            ) + fadeIn() with
                                    slideOutHorizontally(
                                        targetOffsetX = { it / 2 },
                                        animationSpec = tween(250)
                                    ) + fadeOut()
                        }.using(SizeTransform(clip = false))
                    }, label = "MonthChangeAnim"
                ) { _ ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        rows.forEach { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                row.forEach { day ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (day != null) {
                                            val today = remember { Calendar.getInstance() }
                                            val isToday = currentMonth.year == today.get(Calendar.YEAR) &&
                                                    currentMonth.monthValue == today.get(Calendar.MONTH) + 1 &&
                                                    day == today.get(Calendar.DAY_OF_MONTH)

                                            Box(
                                                modifier = Modifier
                                                    .size(42.dp)
                                                    .clip(RoundedCornerShape(50))
                                                    .background(
                                                        when {
                                                            selectedDay == day -> Color(0xFF4A00E0) // highlight selected day
                                                            isToday -> Color(0xFF4A00E0).copy(alpha = 0.6f)
                                                            else -> Color.Transparent
                                                        }
                                                    )
                                                .clickable {
                                                selectedDay = day
                                                val formatted = String.format(
                                                    "%02d/%02d/%04d",
                                                    day,
                                                    currentMonth.monthValue,
                                                    currentMonth.year
                                                )
                                                onDateSelected(formatted) // 👈 Send selected date to MainScreen
                                            },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = day.toString(),
                                                    fontSize = 18.sp,
                                                    fontWeight = if (isToday) FontWeight.W600 else FontWeight.W400,
                                                    color = if (isToday) Color.White else Color.Black.copy(alpha = .7f)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

