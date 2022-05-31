package com.shofiq.roomexample.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {


    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int

    @Query("DELETE FROM subscriberDataTable")
    suspend fun deleteAllData(): Int

    @Query("SELECT * FROM subscriberDataTable")
    fun  getAllSubscriber(): Flow<List<Subscriber>>
}