package com.example.reminderme.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.reminderme.Model.Reminder
import com.example.reminderme.Model.ReminderDatabase
import com.example.reminderme.Model.ReminderRepository
import com.example.reminderme.Notification.NotificationScheduler
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReminderRepository

    init {
        val dao = ReminderDatabase.getDatabase(application).reminderDao()
        repository = ReminderRepository(dao)
    }

    val reminders = repository.allReminders.asLiveData()

    fun addReminder(reminder: Reminder) = viewModelScope.launch {
        repository.insert(reminder)
        NotificationScheduler.scheduleReminder(
            getApplication(), // context
            title = reminder.title,
            date = reminder.date,
            time = reminder.time
        )
    }


    fun updateReminder(reminder: Reminder) = viewModelScope.launch {
        repository.update(reminder)
    }

    fun deleteReminder(reminder: Reminder) = viewModelScope.launch {
        repository.delete(reminder)
    }
}
