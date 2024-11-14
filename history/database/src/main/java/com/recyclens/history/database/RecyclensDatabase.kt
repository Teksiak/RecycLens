package com.recyclens.history.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [HistoryEntity::class],
    version = 1
)
@TypeConverters(HistoryConverters::class)
abstract class RecyclensDatabase: RoomDatabase() {
    abstract val historyDao: HistoryDao
}