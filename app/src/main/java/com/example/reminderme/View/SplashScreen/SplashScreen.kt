package com.example.reminderme.View.SplashScreen


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashScreen(onGetStartedClick: () -> Unit) {



    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8E2DE2), // Purple
            Color(0xFF4A00E0), // Darker Purple
            Color(0xFF00F0FF)  // Aqua
        )
    )
    val scale = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    // ---- Text & Button Slide Animations (Bottom → Top) ----
    val textOffset = remember { Animatable(100f) }     // start lower
    val textAlpha = remember { Animatable(0f) }

    val barOffset = remember { Animatable(120f) }
    val barAlpha = remember { Animatable(0f) }

    val subtitleOffset = remember { Animatable(140f) }
    val subtitleAlpha = remember { Animatable(0f) }

    val buttonOffset = remember { Animatable(160f) }
    val buttonAlpha = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        // --- Icon Animations ---
        scope.launch {
            scale.animateTo(
                targetValue = 1.1f,
                animationSpec = tween(900, easing = EaseOutBack)
            )
            delay(50)
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(400, easing = EaseOutBack)
            )
        }

        scope.launch {
            rotation.animateTo(
                targetValue = 370f,
                animationSpec = tween(1000, easing = LinearEasing)
            )
            rotation.animateTo(
                targetValue = 350f,
                animationSpec = tween(600, easing = LinearEasing)
            )
        }

        // --- Slide-Up Animations (Bottom → Top) ---
        delay(400) // start slightly after icon

        scope.launch {
            textOffset.animateTo(0f, tween(700, easing = FastOutSlowInEasing))
            textAlpha.animateTo(1f, tween(700))
        }

        delay(100)
        scope.launch {
            barOffset.animateTo(0f, tween(700, easing = FastOutSlowInEasing))
            barAlpha.animateTo(1f, tween(700))
        }

        delay(100)
        scope.launch {
            subtitleOffset.animateTo(0f, tween(700, easing = FastOutSlowInEasing))
            subtitleAlpha.animateTo(1f, tween(700))
        }

        delay(100)
        scope.launch {
            buttonOffset.animateTo(0f, tween(700, easing = FastOutSlowInEasing))
            buttonAlpha.animateTo(1f, tween(700))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .scale(scale.value)
                    .rotate(rotation.value)
                    .dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 6.dp,
                            spread = 0.dp,
                            color = Color(0xFFCCCCCC),
                            offset = DpOffset(0.dp, 0.dp)
                        )
                    )
                    .background(Color.White, shape = CircleShape)

                ,
                contentAlignment = Alignment.Center
            ) {
                // Icon in circle
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(gradient, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.AccessTime, // Add your clock icon in drawable
                        contentDescription = "Clock Icon",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            // App name
            Text(
                text = "RemindMe",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.W500,
                letterSpacing = 3.sp,
                modifier = Modifier
                    .alpha(textAlpha.value)
                    .then(Modifier)
                    .offset(y = textOffset.value.dp)
            )
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(4.dp)
                    .alpha(barAlpha.value)
                    .offset(y = barOffset.value.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF).copy(alpha = .7f),
                                Color(0xFF1F3FD5),
                            )
                        )
                    )
            )


            Text(
                text = "Stay organized, stay stress-free",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .alpha(subtitleAlpha.value)
                    .offset(y = subtitleOffset.value.dp)
            )

            // --- Animated Button ---
            ElevatedButton(
                onClick = onGetStartedClick,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 9.dp,
                    pressedElevation = 12.dp,
                    focusedElevation = 10.dp,
                    hoveredElevation = 10.dp
                ),
                modifier = Modifier
                    .alpha(buttonAlpha.value)
                    .offset(y = buttonOffset.value.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color(0xFF4A00E0),
                    fontSize = 16.sp
                )
            }
        }
    }
}


