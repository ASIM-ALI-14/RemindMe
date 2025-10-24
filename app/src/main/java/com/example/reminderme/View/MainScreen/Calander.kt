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
import kotlinx.coroutines.launch
import java.util.*

// NOTE: This implementation supports all API levels.
// It uses YearMonth on API26+, otherwise falls back to Calendar-based generation.
@Composable
fun OptimizedCalendar(onDateSelected: (String) -> Unit) {
    val isApi26Plus = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    // Represent the current month/year as ints so fallback can work the same:
    val (monthState, setMonthState) = remember { mutableStateOf(Pair(Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.YEAR))) }
    // month: 1..12

    // Functions to move month
    fun moveMonth(delta: Int) {
        var (m, y) = monthState
        m += delta
        while (m < 1) { m += 12; y -= 1 }
        while (m > 12) { m -= 12; y += 1 }
        setMonthState(Pair(m, y))
    }

    // Build rows of days for given month/year (works on all API levels)
    val rows = remember(monthState) {
        val (month, year) = monthState
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.MONTH, month - 1)
        cal.set(Calendar.YEAR, year)

        val firstDow = cal.get(Calendar.DAY_OF_WEEK) // 1=Sun..7=Sat
        val leading = (firstDow - 1) % 7 // convert to 0..6 where 0=Sun
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

    // Simple appear animation
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

                                    Box(modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(if (isToday) Color(0xFF4A00E0).copy(alpha = .6f) else Color.Transparent)
                                        .clickable {
                                            val formatted = String.format("%02d/%02d/%04d", day, month, year)
                                            onDateSelected(formatted)
                                        },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = day.toString(), fontSize = 18.sp, color = if (isToday) Color.White else Color.Black.copy(alpha = .7f))
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
