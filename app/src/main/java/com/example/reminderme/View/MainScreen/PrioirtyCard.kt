package com.example.reminderme.View.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.reminderme.Model.Reminder

@Composable
fun PriorityCard(reminder: Reminder) {
    val colorText= when (reminder.priority) {
        "High" -> Color(0xFFFFC482)
        "Medium" -> Color(0xFFFC7373)
        else -> Color(0xFF1C914D)
    }
    val colorbg= when (reminder.priority) {
        "High" -> Color(0xFFFDF8F0)
        "Medium" -> Color(0xFFFFF1F1)
        else -> Color(0xFFE9FDF4)
    }
    val colorbo= when (reminder.priority) {
        "High" -> Color(0xFFF8F1E5)
        "Medium" -> Color(0xFFFDE6E6)
        else -> Color(0xFFCCF8DE)
    }




        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .size( width = if (reminder.priority == "Medium") 134.dp else 124.dp, 40.dp)
                .dropShadow(
                    shape = RoundedCornerShape(10.dp),
                    shadow = Shadow(
                        radius = 8.dp,
                        spread = 0.dp,
                        color =colorbo,
                        offset = DpOffset(0.dp,1.dp)
                    )
                )
                .background(
                    color = colorbg,
                    shape = RoundedCornerShape(20)
                )
                .border(
                    width = 1.dp,
                    color = colorbo,
                    shape = RoundedCornerShape(20)

                )
        ) {
            Text(text = "${reminder.priority}  Priority", color = colorText)

        }



}