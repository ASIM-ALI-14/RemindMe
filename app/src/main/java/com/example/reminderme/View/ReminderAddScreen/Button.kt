package com.example.reminderme.View.ReminderAddScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun Button( onClick: () -> Unit,color: Color,text: Color) {
    val DTOffsetY = remember { Animatable(initialValue = 17f) }
    val DTAlphaY = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        launch {
            DTOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            )
        }
        launch { DTAlphaY.animateTo(1f, animationSpec = tween(600)) }

    }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = DTOffsetY.value.dp)
            .alpha(DTAlphaY.value)
            .height(48.dp)
            .background(color, shape = RoundedCornerShape(40))
            .clickable { onClick() }
    ) {
        Text(text = "Create My Reminder", color=text, fontSize = 17.sp, fontWeight = FontWeight.W700)
    }

}