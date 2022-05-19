package com.shofiq.roomexample.repo

import com.shofiq.roomexample.db.Subscriber
import com.shofiq.roomexample.db.SubscriberDAO

class SubscriberRepository(private val dao: SubscriberDAO) {
    val subscribers = dao.getAllSubscriber()

    suspend fun insert(subscriber: Subscriber){
        dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAllData()
    }
}