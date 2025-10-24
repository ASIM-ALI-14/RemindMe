package com.example.reminderme.Model

import androidx.compose.ui.graphics.Color
data class PriorityInfo(
    val name: String,
    val boxColor: Color,
    val bgColor: Color
)
val priorities = listOf(
    PriorityInfo(
        name = "High",
        boxColor = Color(0xFFF83478),
        bgColor = Color(0xFFFFEBF1)
    ),
    PriorityInfo(
        name = "Medium",
        boxColor = Color(0xFFFC8C00),
        bgColor = Color(0xFFFCF1DA)
    ),
    PriorityInfo(
        name = "Low",
        boxColor = Color(0xFF00C287),
        bgColor = Color(0xFFE5FFF5)
    )
)