package com.recyclens.core.domain

@Dao
interface HistoryRepository {
    @Upsert
    suspend fun addToHistory(history: HistoryEntity)

    @Query("SELECT code FROM History ORDER BY timestamp DESC")
    fun getProductsHistory(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM History")
    suspend fun getHistoryCount(): Int

    @Transaction
    suspend fun deleteLastHistory() {
        val code = getProductsToCorrectHistorySize(1).first()
        deleteFromHistory(code)
        deleteNotFavouriteProduct(code)
    }


}