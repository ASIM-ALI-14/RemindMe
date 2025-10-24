package com.example.reminderme.View.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderme.Model.Reminder


@Composable
fun DescriptionCard(reminder: Reminder) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF8F8F8), shape = RoundedCornerShape(12))
            .border(1.dp, color = Color(0xFFECECEC), shape = RoundedCornerShape(12))
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Description, contentDescription = null, tint = Color(0xFF727273))
            Spacer(modifier = Modifier.width(7.dp))
            Text(text = "Description", fontSize = 18.sp, color = Color(0xFF6D6E70))
        }
        Spacer(modifier = Modifier.height(15.dp))
        reminder.description?.let { Text(text = it, color = Color(0xFF5A5B5D)) }


    }

}