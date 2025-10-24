package com.example.reminderme.View.ReminderAddScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.clickable

@Composable
fun AttachmentCard() {
    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

// 🎯 File picker launcher
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedFileUri = uri
            Toast.makeText(context, "File selected: $uri", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val animatedShadowColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFF6146FF) else Color(0xFFCCCCCC),
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ),
        label = "shadowColorAnimation"
    )
    val DTOffsetY3 = remember { Animatable(initialValue = 20f) }
    val DTAlphaY3 = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(Unit) {
        launch {
            DTOffsetY3.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
            )
        }
        launch { DTAlphaY3.animateTo(1f, animationSpec = tween(1200)) }

    }

    // Outer Box catches taps anywhere to clear focus
    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = DTOffsetY3.value.dp)
            .alpha(DTAlphaY3.value)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus() // reliably clear focus on tap anywhere
                }
            },
        contentAlignment = Alignment.Center
    ) {

        // Inner Box is just the TextField container — no clickable here
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
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
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 📝 Text input area
                Box(modifier = Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(
                            text = "Add a Link, note, or file reference",
                            color = Color(0xFF818181),
                            fontSize = 15.sp
                        )
                    }

                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused
                            }
                    )
                }

                // 📎 Icon at the end
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Attach",
                    tint = if (isFocused) Color(0xFF4736B6) else Color(0xFFB0B0B0),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(22.dp)
                        .clickable {
                            // Open file manager
                            filePickerLauncher.launch("*/*") // 👈 accept any file type
                        }
                )
            }
        }
    }
}