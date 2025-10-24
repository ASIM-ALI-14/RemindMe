package com.example.reminderme.View.ReminderAddScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun TaskProgressBar(
    completedTasks: Int, // current completed tasks (0–3)
    totalTasks: Int = 3
) {
    val progress = (completedTasks.toFloat() / totalTasks.toFloat()).coerceIn(0f, 1f)

    // Smooth animation for progress change
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600, easing = LinearEasing)
    )
    val barOffsetX = remember { Animatable(-20f) }
    val barAlphaX = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        // Run both at same time

        launch {
            barOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            barAlphaX.animateTo(1f, animationSpec = tween(800))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(17.dp)
            .offset(x = barOffsetX.value.dp)
            .alpha(barAlphaX.value)
            .clip(RoundedCornerShape(60))
            .background(Color(0xFFF2F2F7)) // light background
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFDADADA))
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress.value)
                .clip(RoundedCornerShape(50))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFD52299),
                            Color(0xFF0EA1F3), // blue
                            Color(0xFFF37450),


                            Color(0xFF8B76FF),
                        )
                    )
                )
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(50))
        )
    }
}
