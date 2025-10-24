package com.example.reminderme.View.ReminderAddScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch



@Composable
fun ReminderText(text: String, ontext:(String)->Unit) {

    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    val animatedShadowColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFD52299) else Color(0xFFCCCCCC),
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ),
        label = "shadowColorAnimation"
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
            .offset(x = barOffsetX.value.dp)
            .alpha(barAlphaX.value)
            .dropShadow(
                shape = RoundedCornerShape(40.dp),
                shadow = Shadow(
                    radius = if (isFocused) 2.dp else 6.dp,
                    spread = 0.dp,
                    color = animatedShadowColor,
                    offset = DpOffset(0.dp, 0.dp)
                )
            )
            .background(Color.White, RoundedCornerShape(40.dp))
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
            .height(45.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = text,
            onValueChange = {ontext(it)},
            singleLine = true,
            textStyle = TextStyle(color = Color.Gray, fontSize = 14.sp),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty()) { // 👈 Only hide placeholder when text is typed
                        Text(
                            text = "What would you like to be reminded of?",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}