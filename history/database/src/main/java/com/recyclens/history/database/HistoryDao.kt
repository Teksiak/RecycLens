package com.recyclens.history.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM History ORDER BY date DESC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Transaction
    suspend fun addToHistory(historyEntity: HistoryEntity, historySize: Int) {
        upsertHistory(historyEntity)
        if(getHistoryCount() > historySize) {
            deleteLastHistory()
        }
    }

    @Query("DELETE FROM History WHERE id = :id")
    suspend fun deleteHistoryById(id: Int)

    @Transaction
    suspend fun correctHistorySize(correction: Int) {
        val entitiesToRemove = getOldestHistoryIds(correction)
        deleteEntitiesByIds(*entitiesToRemove.toIntArray())
    }

    @Upsert
    suspend fun upsertHistory(historyEntity: HistoryEntity)

    @Query("SELECT COUNT(*) FROM History")
    suspend fun getHistoryCount(): Int

    @Transaction
    suspend fun deleteLastHistory() {
        val code = getOldestHistoryIds(1).first()
        deleteEntitiesByIds(code)
    }

    @Query("DELETE FROM History WHERE id IN (:ids)")
    suspend fun deleteEntitiesByIds(vararg ids: Int)

    @Query("SELECT id FROM History ORDER BY date ASC LIMIT :size")
    suspend fun getOldestHistoryIds(size: Int): List<Int>



}