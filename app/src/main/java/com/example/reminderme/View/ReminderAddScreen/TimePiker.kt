package com.example.reminderme.View.ReminderAddScreen


import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import java.util.*

@Composable
fun TimePickerBox(
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {

    val context = LocalContext.current

    // ⏰ Current time
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // 🕒 TimePickerDialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val formattedHour = if (selectedHour < 10) "0$selectedHour" else "$selectedHour"
            val formattedMinute = if (selectedMinute < 10) "0$selectedMinute" else "$selectedMinute"
            val formattedTime = "$formattedHour:$formattedMinute"
            onTimeSelected(formattedTime) // 👉 send back to parent
        },
        hour, minute, true
    )

    // 🎨 Time Box UI
    Box(
        modifier = Modifier
            .size(164.dp, 40.dp)
            .dropShadow(
                shape = RoundedCornerShape(40),
                shadow = Shadow(
                    radius = 4.dp,
                    spread = 0.dp,
                    color = Color(0xFFCCCCCC),
                    offset = DpOffset(0.dp, 0.dp)
                )
            )
            .background(Color.White, RoundedCornerShape(20.dp))
            .clickable { timePickerDialog.show() }
            .padding(top = 5.dp),
        contentAlignment = Alignment.Center


    ) {
        Row() {
            Text(
                text = if (selectedTime.isEmpty()) "--:--" else selectedTime,
                color = if (selectedTime == "Select Time") Color(0xFF888888) else Color(0xFF282828),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

        }
    }
}
