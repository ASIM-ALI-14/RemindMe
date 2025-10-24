package com.example.reminderme.Veiw.ReminderAddScreen

import android.R.attr.priority
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderme.Model.Reminder

import com.example.reminderme.Model.priorities
import com.example.reminderme.VeiwModel.ReminderViewModel
import kotlinx.coroutines.launch


@Composable
fun AddReminderScreen(onNavigateBack: () -> Unit, viewModel: ReminderViewModel) {
    val cardOffsetY = remember { Animatable(-20f) }
    val cardAlphaY = remember { Animatable(0f) }
    val barOffsetX = remember { Animatable(-20f) }
    val barAlphaX = remember { Animatable(0f) }
    val barOffsetY = remember { Animatable(20f) }
    val barAlphaY = remember { Animatable(0f) }
    val DTOffsetY = remember { Animatable(initialValue = 20f) }
    val DTAlphaY = remember { Animatable(initialValue = 0f) }
    val DTOffsetY2 = remember { Animatable(initialValue = 20f) }
    val DTAlphaY2 = remember { Animatable(initialValue = 0f) }
    val DTOffsetY3 = remember { Animatable(initialValue = 20f) }
    val DTAlphaY3 = remember { Animatable(initialValue = 0f) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var attachmentUri by remember { mutableStateOf<String?>(null) }
    var selectedPriority by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val completedSteps = listOf(
        title.isNotEmpty(), // 1 - title entered
        time.isNotEmpty(),  // 2 - time selected
        date.isNotEmpty()   // 3 - date selected
    ).count { it }

    LaunchedEffect(Unit) {
        // Run both at same time
        launch {
            cardOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            DTOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }
        launch {
            DTOffsetY3.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
            )
        }
        launch {
            barOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            DTOffsetY2.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
            )
        }
        launch {
            barOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            cardAlphaY.animateTo(1f, animationSpec = tween(800))
        }
        launch {
            barAlphaX.animateTo(1f, animationSpec = tween(800))
        }
        launch {
            barAlphaY.animateTo(1f, animationSpec = tween(800))
        }
        launch {
            DTAlphaY.animateTo(1f, animationSpec = tween(700))
        }
        launch {
            DTAlphaY2.animateTo(1f, animationSpec = tween(900))
        }
        launch {
            DTAlphaY3.animateTo(1f, animationSpec = tween(1200))
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDBE1F1))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 23.dp, vertical = 45.dp)
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = cardOffsetY.value.dp)
                    .alpha(cardAlphaY.value)
            ) {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .clip(shape = RoundedCornerShape(30))
                        .clickable { onNavigateBack() }


                        .dropShadow(
                            shape = RoundedCornerShape(10.dp), shadow = Shadow(
                                radius = 6.dp,
                                spread = 0.dp,
                                color = Color(0xFFCCCCCC),
                                offset = DpOffset(0.dp, 1.dp)
                            )
                        )

                        .background(Color.White, shape = RoundedCornerShape(30))

                        .padding(12.dp)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)

                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(text = "Create Magic", fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Organize your thoughts ", fontSize = 15.sp, color = Color(
                            0xFF595959
                        )
                    )
                }
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier


                        .dropShadow(
                            shape = RoundedCornerShape(10.dp), shadow = Shadow(
                                radius = 6.dp,
                                spread = 0.dp,
                                color = Color(0xFFCCCCCC),
                                offset = DpOffset(0.dp, 1.dp)
                            )
                        )
                        .background(Color.White, shape = RoundedCornerShape(30))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.width(58.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(25.dp)
                                .background(Color.Transparent, shape = CircleShape)
                                .border(2.dp, color = Color(0x458080A6), shape = CircleShape)
                        ) {
                            Text(text =  completedSteps.toString())
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "/3")
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            TaskProgressBar(completedTasks = completedSteps)
            Spacer(modifier = Modifier.height(23.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .offset(x = barOffsetX.value.dp)
                    .alpha(barAlphaX.value)
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(Color(0xFFD52299), shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(11.dp))
                Text(text = "Reminder Title")
            }
            Spacer(modifier = Modifier.height(11.dp))
            ReminderText(text = title, ontext = { title = it })
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .offset(x = barOffsetX.value.dp)
                    .alpha(barAlphaX.value)
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(Color(0xFF0EA1F3), shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(11.dp))
                Row() {
                    Text(text = "Description")
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(text = "Optional")
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            DescriptionCard(description,{description =it})
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .offset(y = DTOffsetY.value.dp)
                    .alpha(DTAlphaY.value)
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(Color(0xFFF27259), shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(11.dp))
                Row {
                    Text(text = "Priority Level")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(11.dp),
                modifier = Modifier
                    .offset(y = DTOffsetY.value.dp)
                    .alpha(DTAlphaY.value)
            ) {
                items(priorities) { priority ->
                    val isSelected = selectedPriority == priority.name
                    val animatedColor by animateColorAsState(
                        targetValue = if (isSelected) priority.bgColor else Color.White,
                        animationSpec = tween(300)
                    )
                    PorityCard(
                        bgColor = if (isSelected) animatedColor else Color.White,
                        name = priority.name,
                        boxColor = priority.boxColor,
                        onclick = { selectedPriority = priority.name
                            focusManager.clearFocus()}
                                )

                }
            }

            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier
                    .offset(y = DTOffsetY2.value.dp)
                    .alpha(DTAlphaY2.value)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFF36BB97), shape = RoundedCornerShape(25)
                                )
                                .padding(6.dp)
                        ) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(13.dp))
                        Text(text = "Date")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    DatePickerBox(date, onDateSelected = { newDate ->
                        date = newDate
                    })
                }
                Spacer(modifier = Modifier.width(18.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color(0xFFF96936), shape = RoundedCornerShape(25)
                                )
                                .padding(6.dp)
                        ) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(13.dp))
                        Text(text = "Time")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    TimePickerBox(
                        selectedTime = time,
                        onTimeSelected = { time = it })
                }
            }


            Spacer(modifier = Modifier.height(25.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .offset(y = DTOffsetY3.value.dp)
                    .alpha(DTAlphaY3.value)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFF8B76FF), shape = RoundedCornerShape(25)
                        )
                        .padding(6.dp)
                ) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(13.dp))
                Text(text = "Attachment")
                Spacer(modifier = Modifier.width(13.dp))
                Text(text = "optional")
            }
            Spacer(modifier = Modifier.height(13.dp))
            AttachmentCard()
            Spacer(modifier = Modifier.height(23.dp))
            val buttonColor = if(completedSteps==3) Color(0xFF0BC245) else Color(0xA6EAEFF9)
            val textColor = if(completedSteps==3) Color.White else Color(0x686E7179)
            Button(onClick = {
                if (title.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                    val reminder = Reminder(
                        title = title,
                        description = description,
                        date = date,
                        time = time,
                        priority = selectedPriority

                    )
                    viewModel.addReminder(reminder)
                    onNavigateBack()
                }
            },buttonColor,textColor)
            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}