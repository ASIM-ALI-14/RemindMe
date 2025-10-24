package com.example.reminderme.Veiw.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderme.Model.Reminder


@Composable
fun ScheduledTimeCard(reminder: Reminder) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFECF8FC), shape = RoundedCornerShape(12))
            .border(1.dp, color = Color(0xFFC3DFE5), shape = RoundedCornerShape(12))
            .padding(horizontal = 12.dp, vertical = 19.dp)

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF009FF0), shape = RoundedCornerShape(30))
                    .padding(10.dp)
            ) {
                Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = "Scheduled Time", fontSize = 12.sp, fontWeight = FontWeight.W300)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = reminder.time, fontSize = 17.sp, fontWeight = FontWeight.W400)
            }
        }
    }
}