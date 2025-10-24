package com.example.reminderme.Model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminder_table ORDER BY date, time ASC")
    fun getAllReminders(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminder_table WHERE id = :id LIMIT 1")
    suspend fun getReminderById(id: Int): Reminder?
}
