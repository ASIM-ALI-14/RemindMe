package com.example.reminderme.View.MainScreen


import android.text.format.DateFormat
import android.widget.Toast

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
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
import com.example.reminderme.ViewModel.ReminderViewModel


import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    viewModel: ReminderViewModel,
    onNavigateToAddReminder: () -> Unit
) {
    val remindersState = viewModel.reminders.observeAsState(emptyList())
    var selectedDate by remember { mutableStateOf<String?>(null) }

    val filteredReminders = remember(remindersState.value, selectedDate) {
        if (selectedDate == null) remindersState.value
        else remindersState.value.filter { it.date == selectedDate }
    }

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF8E2DE2),
            Color(0xFF4A00E0),
            Color(0xFF00F0FF)
        )
    )

    val currentTime = remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = System.currentTimeMillis()
            delay(1000)
        }
    }

    val scale = remember { Animatable(0f) }
    val draggingReminder = remember { mutableStateOf<Reminder?>(null) }
    val showDeleteIcon = remember { mutableStateOf(false) }
    val isOverDeleteIcon = remember { mutableStateOf(false) }

    val formattedTime = DateFormat.format("hh:mm", currentTime.value)
    val formattedAMPM = DateFormat.format("a", currentTime.value)
    val formattedDay = DateFormat.format("EEEE,", currentTime.value)
    val formattedDate = DateFormat.format("MMMM dd", currentTime.value)

    val textOffsetX = remember { Animatable(-50f) }
    val cardOffsetX = remember { Animatable(50f) }
    val textAlpha = remember { Animatable(0f) }
    val cardAlpha = remember { Animatable(0f) }
    val cardOffsetY = remember { Animatable(50f) }
    val cardAlphaY = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    // 🔹 Animations for intro
    LaunchedEffect(Unit) {
        launch {
            textOffsetX.animateTo(0f, tween(1000, easing = FastOutSlowInEasing))
        }
        launch {
            cardOffsetX.animateTo(0f, tween(1000, easing = FastOutSlowInEasing))
        }
        launch {
            cardOffsetY.animateTo(0f, tween(700, easing = FastOutSlowInEasing))
        }
        scope.launch {
            scale.animateTo(1.1f, tween(1200, easing = EaseOutBack))
            scale.animateTo(1f, tween(1200, easing = EaseOutBack))
        }
        launch { textAlpha.animateTo(1f, tween(800)) }
        launch { cardAlpha.animateTo(1f, tween(700)) }
        launch { cardAlphaY.animateTo(1f, tween(800)) }
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
            // 🔹 Header Section
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .offset(x = textOffsetX.value.dp)
                        .alpha(textAlpha.value)
                ) {
                    Text("Hi, Tom", fontSize = 40.sp)
                    Text(
                        "Ready to conquer\nyour day?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.Black.copy(alpha = .7f)
                    )
                }

                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
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
                            "$formattedTime",
                            fontSize = 31.sp,
                            style = TextStyle(brush = gradient),
                            fontWeight = FontWeight.W800
                        )
                        Text(
                            "$formattedAMPM",
                            style = TextStyle(brush = gradient),
                            fontWeight = FontWeight.W700,
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "$formattedDay",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black.copy(alpha = .7f)
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            "$formattedDate",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Black.copy(alpha = .7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(39.dp))

            // 🔹 Calendar
            OptimizedCalendar(onDateSelected = { date -> selectedDate = date })

            Spacer(modifier = Modifier.height(30.dp))

            // 🔹 Reminder Header
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
                    Text("${filteredReminders.size} Tasks")
                }
            }

            Spacer(modifier = Modifier.height(23.dp))

            // 🔹 Reminders List
            if (filteredReminders.isEmpty()) {
                NoReminderCard()
            } else {
                filteredReminders.forEach { reminder ->
                    val isDraggingThis = draggingReminder.value == reminder
                    val offsetX = remember { Animatable(0f) }
                    val offsetY = remember { Animatable(0f) }

                    Box(
                        modifier = Modifier
                            .zIndex(if (isDraggingThis) 10f else 0f)
                            .offset {
                                if (isDraggingThis)
                                    IntOffset(offsetX.value.toInt(), offsetY.value.toInt())
                                else IntOffset(0, 0)
                            }
                            .graphicsLayer(
                                scaleX = if (isDraggingThis) 0.93f else 1f,
                                scaleY = if (isDraggingThis) 0.93f else 1f
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
                                                draggingReminder.value = null
                                                offsetY.animateTo(
                                                    offsetY.value + 400f,
                                                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                                                )
                                                delay(150)
                                                viewModel.deleteReminder(reminder)
                                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                Toast.makeText(context, "Reminder deleted", Toast.LENGTH_SHORT).show()
                                            } else {
                                                draggingReminder.value = null
                                                showDeleteIcon.value = false
                                                isOverDeleteIcon.value = false
                                                offsetX.animateTo(0f, tween(400))
                                                offsetY.animateTo(0f, tween(400))
                                            }

                                            showDeleteIcon.value = false
                                            isOverDeleteIcon.value = false
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
        }

        // 🔹 Floating Add Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .size(60.dp)
                .scale(scale.value)
                .background(gradient, CircleShape)
                .clip(CircleShape)
                .clickable { onNavigateToAddReminder() }
            ,contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(35.dp))
        }

        // 🔹 Delete Icon
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
                    .size(70.dp)
                    .scale(deleteScale)
                    .shadow(16.dp, CircleShape)
                    .background(deleteColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Reminder", tint = Color.White, modifier = Modifier.size(32.dp))
            }
        }
    }
}
