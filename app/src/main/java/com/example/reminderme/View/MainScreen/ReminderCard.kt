package com.example.reminderme.View.MainScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.scale

import java.text.SimpleDateFormat
import java.util.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.reminderme.Model.Reminder
import kotlinx.coroutines.launch

@Composable
fun ReminderCard(
    reminder: Reminder,
    isDragging: Boolean = false // 👈 add this to scale card while dragging
) {
    // 🕒 Check if reminder is expired
    // 🕒 Check if reminder is expired
    val isExpired by remember(reminder.date, reminder.time) {
        derivedStateOf {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            val dateTimeString = "${reminder.date} ${reminder.time}"
            val reminderDate = try { sdf.parse(dateTimeString) } catch (e: Exception) { null }
            reminderDate?.before(Date()) == true
        }
    }





    val cardOffsetY = remember { Animatable(50f) }
    val cardAlphaY = remember { Animatable(0f) }

    // Smooth appear animation
    LaunchedEffect(Unit) {
        launch {
            cardOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
            )
        }
        launch {
            cardAlphaY.animateTo(1f, animationSpec = tween(700))
        }
    }

    // Smooth drag scale
    val dragScale by animateFloatAsState(
        targetValue = if (isDragging) 0.92f else 1f,
        animationSpec = tween(250, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = cardOffsetY.value.dp)
            .alpha(cardAlphaY.value)
            .scale(dragScale)
            .zIndex(if (isDragging) 10f else 0f)
            .dropShadow(
                shape = RoundedCornerShape(20),
                shadow = Shadow(
                    radius = 6.dp,
                    color =if(isDragging) Color.Transparent else Color(0x0F494949),
                    spread = 1.dp,
                    offset = DpOffset(0.dp, 1.dp)

                )
            )
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 22.dp, vertical = 26.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 🟣 Reminder Priority
            PriorityCard(reminder)

            // 🟢 Title
            Text(
                text = reminder.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(start = 3.dp)
            )

            // 🔵 Scheduled Time
            ScheduledTimeCard(reminder)

            // 🟡 Description
            DescriptionCard(reminder)

            // 🔘 Footer
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Date", fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
//
                    Text(reminder.date, color = Color(0xFF444444))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Status", fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isExpired) "Completed" else "Pending",
                        color = if (isExpired) Color(0xFF1C914D) else Color(0xFFE58585),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
