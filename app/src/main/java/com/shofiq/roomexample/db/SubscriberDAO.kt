package com.shofiq.roomexample.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {


    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriberDataTable")
    suspend fun deleteAllData()

    @Query("SELECT * FROM subscriberDataTable")
    fun  getAllSubscriber(): Flow<List<Subscriber>>
}