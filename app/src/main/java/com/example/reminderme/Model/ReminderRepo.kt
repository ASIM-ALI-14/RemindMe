package com.example.reminderme.Model

class ReminderRepository(private val reminderDao: ReminderDao) {

    val allReminders = reminderDao.getAllReminders()

    suspend fun insert(reminder: Reminder) = reminderDao.insertReminder(reminder)
    suspend fun update(reminder: Reminder) = reminderDao.updateReminder(reminder)
    suspend fun delete(reminder: Reminder) = reminderDao.deleteReminder(reminder)
    suspend fun getReminderById(id: Int) = reminderDao.getReminderById(id)
}
