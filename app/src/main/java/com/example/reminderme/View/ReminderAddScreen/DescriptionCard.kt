package com.example.reminderme.View.ReminderAddScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch



@Composable
fun DescriptionCard(text: String, ontext:(String)->Unit) {

    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val animatedShadowColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFF0EA1F3) else Color(0xFFCCCCCC),
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "shadowColorAnimation"
    )
    val barOffsetY = remember { Animatable(20f) }
    val barAlphaY = remember { Animatable(0f) }
    var description by remember { mutableStateOf("") }
    // Outer box: detects tap to focus or clear focus
    LaunchedEffect(Unit) {
        // Run both at same time

        launch {
            barOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            barAlphaY.animateTo(1f, animationSpec = tween(800))
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = barOffsetY.value.dp)
            .alpha(barAlphaY.value)
            .height(120.dp)
            .dropShadow(
                shape = RoundedCornerShape(20.dp),
                shadow = Shadow(
                    radius = if (isFocused) 2.dp else 6.dp,
                    spread = 0.dp,
                    color = animatedShadowColor,
                    offset = DpOffset(0.dp, 0.dp)
                )
            )
            .background(Color.White, RoundedCornerShape(20.dp))
           ,
        contentAlignment = Alignment.TopStart
    ) {
        BasicTextField(
            value = text,
            onValueChange = { ontext(it)},
            textStyle = TextStyle(color = Color.Gray, fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (!focusState.isFocused) {
                        focusManager.clearFocus()
                    }
                },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = "Add more detail about your\nreminder..",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}
