package com.example.reminderme.Veiw.MainScreen



import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Preview
@Composable
fun NoReminderCard() {
    val cardOffsetY = remember { Animatable(50f) }
    val cardAlphaY = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        // Run both at same time
        launch {
            cardOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
            )
        }

        launch {
            cardAlphaY.animateTo(1f, animationSpec = tween(900))
        }
    }
    Box(
        modifier = Modifier.fillMaxWidth().height(373.dp).offset(y = cardOffsetY.value.dp)
            .alpha(cardAlphaY.value)
         
            .dropShadow(
                shape = RoundedCornerShape(20),
                shadow = Shadow(
                    radius = 6.dp,
                    color = Color(0x0F494949),
                    spread = 1.dp,
                    offset = DpOffset(0.dp, 1.dp)

                )
            )
            .background(Color.White, shape = RoundedCornerShape(20.dp))

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFE1E4EC), shape = CircleShape)
                    .padding(20.dp)
            ) {
                Icon(
                    Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Color(0xFF8D9CC4),
                    modifier = Modifier.size(35.dp)
                )

            }
            Spacer(modifier = Modifier.height(22.dp))

            Text(text = "No Reminders", fontSize = 23.sp, color = Color(0xFF494A4D), fontWeight = FontWeight.W400)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "No reminders scheduled for Friday,", fontSize = 12.sp, color = Color(
                0xFF717379
            )
            )
            Text(text = "October 3, 2025", fontSize = 12.sp, color = Color(0xFF717379))

        }

    }

}