package com.example.reminderme.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_table")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val status: String = "Pending",
    val priority: String = "Low" // added to support PriorityCard
)
