package com.shofiq.roomexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shofiq.roomexample.db.Subscriber
import com.shofiq.roomexample.repo.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber


    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            updateSubscriber(subscriberToUpdateOrDelete)
        }
        else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insertSubscriber(Subscriber(0, name, email))
            inputEmail.value = null
            inputName.value = null
        }
    }

    fun clearOrDelete() {
        if (isUpdateOrDelete) {
            deleteSubscriber(subscriberToUpdateOrDelete)
        }else {
            deleteAll()
        }
    }

    private fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.insert(subscriber)
    }

    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.update(subscriber)
        inputEmail.value = null
        inputName.value = null
        isUpdateOrDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Delete all"
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
        inputEmail.value = null
        inputName.value = null
        isUpdateOrDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Delete all"
    }

    private fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber

        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }
}