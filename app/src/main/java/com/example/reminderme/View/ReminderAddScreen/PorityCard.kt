package com.example.reminderme.View.ReminderAddScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PorityCard(bgColor: Color, name: String, boxColor: Color,onclick:()->Unit) {

        Box(
            modifier = Modifier

                .size(104.dp, 90.dp)
                .clip(shape = RoundedCornerShape(10))
                .dropShadow(
                    shape = RoundedCornerShape(10),
                    shadow = Shadow(
                        radius = 3.dp,
                        spread = 0.dp,
                        color = Color(0xFFCCCCCC),
                        offset = DpOffset(0.dp, 0.dp)
                    )
                )
                .background(bgColor, shape = RoundedCornerShape(10))


                .clickable { onclick() }
                .padding(vertical = 10.dp, horizontal = 19.dp)

                    // 🟢 update on click

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(boxColor, shape = CircleShape)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }

}