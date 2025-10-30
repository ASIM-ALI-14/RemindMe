// OptimizedCalendar.kt (replace existing)
package com.example.reminderme.View.MainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderme.Model.Reminder // 👈 Import Reminder model
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun OptimizedCalendar(
    reminders: List<Reminder>,      // 👈 Added: To check for dots
    selectedDate: String?,         // 👈 Added: To show selection
    onDateSelected: (String) -> Unit
) {
    val (monthState, setMonthState) = remember { mutableStateOf(Pair(Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR))) }

    fun moveMonth(delta: Int) {
        var (m, y) = monthState
        m += delta
        while (m < 1) { m += 12; y -= 1 }
        while (m > 12) { m -= 12; y += 1 }
        setMonthState(Pair(m, y))
    }

    val rows = remember(monthState) {
        val (month, year) = monthState
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.MONTH, month - 1)
        cal.set(Calendar.YEAR, year)

        val firstDow = cal.get(Calendar.DAY_OF_WEEK)
        val leading = (firstDow - 1) % 7
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val totalCells = ((leading + daysInMonth + 6) / 7) * 7

        val cells = List(totalCells) { index ->
            val day = index - leading + 1
            if (index < leading || day > daysInMonth) null else day
        }
        cells.chunked(7)
    }

    val (month, year) = monthState
    val displayMonthName = remember(monthState) {
        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, month - 1)
        cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""
    }

    val cardOffsetY = remember { Animatable(50f) }
    val cardAlphaY = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        launch { cardOffsetY.animateTo(0f, animationSpec = tween(600, easing = FastOutSlowInEasing)) }
        launch { cardAlphaY.animateTo(1f, animationSpec = tween(600)) }
    }

    Surface(
        modifier = Modifier
            .height(if (rows.size == 6) 399.dp else 365.dp)
            .offset(y = cardOffsetY.value.dp)
            .alpha(cardAlphaY.value),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { moveMonth(-1) }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = Color(0xFF4A00E0))
                }
                Text(text = "$displayMonthName $year", fontSize = 23.sp, fontWeight = FontWeight.W600, color = Color.Black.copy(alpha = .7f))
                IconButton(onClick = { moveMonth(+1) }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF4A00E0))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                    Text(text = it, modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 18.sp, color = Color.Black.copy(alpha = .7f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                rows.forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        row.forEach { day ->
                            Box(modifier = Modifier.weight(1f).aspectRatio(1f).padding(4.dp), contentAlignment = Alignment.Center) {
                                if (day != null) {
                                    val todayCal = Calendar.getInstance()
                                    val isToday = (year == todayCal.get(Calendar.YEAR) && month == todayCal.get(Calendar.MONTH) + 1 && day == todayCal.get(Calendar.DAY_OF_MONTH))

                                    // ⭐️ START: --- Logic Update ---
                                    val formattedDate = String.format("%02d/%02d/%04d", day, month, year)
                                    val isSelected = selectedDate == formattedDate
                                    val hasReminder = reminders.any { it.date == formattedDate }

                                    // Determine background color
                                    val bgColor = when {
                                        isSelected -> Color(0xFF4A00E0) // 1. Selected date
                                        isToday -> Color(0xFF4A00E0).copy(alpha = .6f) // 2. Today's date
                                        else -> Color.Transparent
                                    }

                                    // Determine text color
                                    val textColor = when {
                                        isSelected || isToday -> Color.White // Text for selected or today
                                        else -> Color.Black.copy(alpha = .7f)
                                    }

                                    Box(modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(bgColor) // 👈 Use dynamic bg color
                                        .clickable {
                                            onDateSelected(formattedDate)
                                        },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = day.toString(), fontSize = 18.sp, color = textColor) // 👈 Use dynamic text color

                                        // ⭐️ ADDED: Show dot if there's a reminder and it's not selected/today
                                        if (hasReminder && !isSelected && !isToday) {
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFF4A00E0).copy(alpha = .7f))
                                                    .align(Alignment.BottomCenter)
                                                    .offset(y = (-6).dp)
                                            )
                                        }
                                        // ⭐️ END: --- Logic Update ---
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