package com.example.reminderme.View.ReminderAddScreen

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun DatePickerBox(
    selectedDate: String?,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current

    // 📅 Current date
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // 📆 Date picker dialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected("$selectedDay/${selectedMonth + 1}/$selectedYear")
        },
        year, month, day
    )

    // 🟣 If no date selected, show "dd/mm/yyyy"
    val displayText = if (selectedDate.isNullOrEmpty()) "dd/mm/yyyy" else selectedDate
    val displayColor = if (selectedDate.isNullOrEmpty()) Color(0xFFAAAAAA) else Color(0xFF282828)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(164.dp, 40.dp)
            .dropShadow(
                shape = RoundedCornerShape(40.dp),
                shadow = Shadow(
                    radius = 4.dp,
                    spread = 0.dp,
                    color = Color(0xFFCCCCCC),
                    offset = DpOffset(0.dp, 0.dp)
                )
            )
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clickable { datePickerDialog.show() }
    ) {
        Text(
            text = displayText,
            fontSize = 19.sp,
            color = displayColor,
            textAlign = TextAlign.Center
        )
    }
}
