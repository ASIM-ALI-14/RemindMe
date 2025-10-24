package com.example.reminderme.Veiw.MainScreen

import android.os.Build
import android.text.format.DateFormat
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

import com.example.reminderme.Model.Reminder
import com.example.reminderme.VeiwModel.ReminderViewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun MainScreen(
    viewModel: ReminderViewModel, onNavigateToAddReminder: () -> Unit
) {
    val remindersState = viewModel.reminders.observeAsState(emptyList())

    var selectedDate by remember { mutableStateOf<String?>(null) }

    val filteredReminders = remember(remindersState.value, selectedDate) {
        if (selectedDate == null) remindersState.value
        else remindersState.value.filter { it.date == selectedDate }
    }
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current


    val dragScale = remember { Animatable(1f) }

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF8E2DE2), // Purple
            Color(0xFF4A00E0), // Darker Purple
            Color(0xFF00F0FF)  // Aqua
        )
    )
    val currentTime = remember {
        mutableStateOf(System.currentTimeMillis())
    }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = System.currentTimeMillis()
            delay(1000) // 1 second
        }
    }
    val scale = remember { Animatable(0f) }
    val draggingReminder = remember { mutableStateOf<Reminder?>(null) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val showDeleteIcon = remember { mutableStateOf(false) }
    val isOverDeleteIcon = remember { mutableStateOf(false) }

    val formettime = DateFormat.format("hh:mm", currentTime.value)
    val formetAMPM = DateFormat.format("a", currentTime.value)
    val formetDay = DateFormat.format("EEEE,", currentTime.value)
    val formetDate = DateFormat.format("MMMM dd", currentTime.value)
    val textOffsetX = remember { Animatable(-50f) }  // start off-screen left
    val cardOffsetX = remember { Animatable(50f) }   // start off-screen right
    val textAlpha = remember { Animatable(0f) }
    val cardAlpha = remember { Animatable(0f) }
    val cardOffsetY = remember { Animatable(50f) }
    val cardAlphaY = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Run both at same time
        launch {
            textOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
        launch {
            cardOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
        launch {
            cardOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }
        scope.launch {
            scale.animateTo(
                targetValue = 1.1f, animationSpec = tween(1200, easing = EaseOutBack)
            )

            scale.animateTo(
                targetValue = 1f, animationSpec = tween(1200, easing = EaseOutBack)
            )
        }
        // Fade in
        launch {
            textAlpha.animateTo(1f, animationSpec = tween(800))
        }
        launch {
            cardAlpha.animateTo(1f, animationSpec = tween(700))
        }
        launch {
            cardAlphaY.animateTo(1f, animationSpec = tween(800))
        }


    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDBE1F1))
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 23.dp, vertical = 45.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .offset(x = textOffsetX.value.dp)
                        .alpha(textAlpha.value)
                ) {
                    Text(text = "Hi, Tom", fontSize = 40.sp)
                    Text(
                        text = "Ready to conquer\nyour day?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.Black.copy(alpha = .7f)
                    )
                }
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White// 👈 Your custom color
                    ),
                    modifier = Modifier
                        .size(155.dp, 163.dp)
                        .offset(x = cardOffsetX.value.dp)
                        .alpha(cardAlpha.value)

                ) {
                    Column(
                        modifier = Modifier
                            .padding(18.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "$formettime", fontSize = 31.sp, style = TextStyle(
                                brush = gradient // use your gradient defined above
                            ), fontWeight = FontWeight.W800
                        )
                        Text(
                            text = "$formetAMPM", style = TextStyle(
                                brush = gradient // use your gradient defined above
                            ), fontWeight = FontWeight.W700, fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "$formetDay",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black.copy(alpha = .7f)
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "$formetDate",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black.copy(alpha = .7f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(39.dp))
            OptimizedCalendar(onDateSelected = { date ->
                selectedDate = date
            })
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = cardOffsetY.value.dp)
                    .alpha(cardAlphaY.value)
            ) {
                Box(
                    modifier = Modifier
                        .background(gradient, shape = RoundedCornerShape(24))
                        .padding(12.dp)
                ) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.White)
                }
                Text("Today's Reminders", fontSize = 23.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(gradient, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("0 Tasks")
                }
            }
            Spacer(modifier = Modifier.height(23.dp))
            if (filteredReminders.isEmpty()) {
                NoReminderCard()
            } else {
                filteredReminders.forEach { reminder ->
                    val isDraggingThis = draggingReminder.value == reminder

                    // 👇 Local offset for each reminder (unique per item)
                    val offsetX = remember { Animatable(0f) }
                    val offsetY = remember { Animatable(0f) }

                    Box(
                        modifier = Modifier
                            .zIndex(if (isDraggingThis) 10f else 0f)
                            .offset {
                                if (isDraggingThis) {
                                    IntOffset(offsetX.value.toInt(), offsetY.value.toInt())
                                } else IntOffset(0, 0)
                            }
                            .graphicsLayer(
                                scaleX = if (isDraggingThis) 0.93f else 1f,
                                scaleY = if (isDraggingThis) 0.93f else 1f,
                            )
                            .pointerInput(reminder.id) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = {
                                        draggingReminder.value = reminder
                                        showDeleteIcon.value = true
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        scope.launch {
                                            offsetX.snapTo(offsetX.value + dragAmount.x)
                                            offsetY.snapTo(offsetY.value + dragAmount.y)
                                        }

                                        val deleteZoneTop = 850f
                                        val deleteZoneBottom = 1350f
                                        val deleteZoneHorizontal = -600f..600f
                                        val cardHeightPx = 600f
                                        val cardBottomY = offsetY.value + (cardHeightPx * 0.7f)

                                        isOverDeleteIcon.value =
                                            cardBottomY in deleteZoneTop..deleteZoneBottom &&
                                                    offsetX.value in deleteZoneHorizontal
                                    },
                                    onDragEnd = {
                                        scope.launch {
                                            if (isOverDeleteIcon.value) {
                                                offsetY.animateTo(
                                                    offsetY.value + 400f,
                                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                                )
                                                delay(150)
                                                viewModel.deleteReminder(reminder)
                                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                Toast.makeText(context, "Reminder deleted", Toast.LENGTH_SHORT).show()
                                            }

                                            draggingReminder.value = null
                                            showDeleteIcon.value = false
                                            isOverDeleteIcon.value = false
                                            offsetX.animateTo(0f, tween(400))
                                            offsetY.animateTo(0f, tween(400))
                                        }
                                    }
                                )
                            }
                    ) {
                        ReminderCard(reminder = reminder, isDragging = isDraggingThis)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            Spacer(modifier = Modifier.height(23.dp))


            Spacer(modifier = Modifier.height(14.dp))
        }


        Spacer(modifier = Modifier.height(12.dp))


        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(12.dp))



        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .size(60.dp)
                .scale(scale.value)
                .background(gradient, CircleShape)
                .clip(CircleShape)
                .clickable { onNavigateToAddReminder() }, contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }

        // Delete Icon
        if (showDeleteIcon.value) {
            val deleteColor by animateColorAsState(
                targetValue = if (isOverDeleteIcon.value) Color(0xFFFF5C5C) else Color(0xFFD1D1D1),
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
            val deleteScale by animateFloatAsState(
                targetValue = if (isOverDeleteIcon.value) 1.25f else 1f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .size(70.dp) // 👈 smaller, more elegant
                    .scale(deleteScale)
                    .shadow(16.dp, CircleShape)
                    .background(deleteColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Reminder",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }


    }
}