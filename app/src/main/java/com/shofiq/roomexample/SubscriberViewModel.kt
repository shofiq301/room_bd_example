package com.shofiq.roomexample

import androidx.lifecycle.LiveData
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

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
    get() = statusMessage
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
        val newRowId = repository.insert(subscriber)
        if (newRowId > -1) {
            statusMessage.value = Event("Subscriber inserted successfully in $newRowId")
        }else {
            statusMessage.value = Event("Error occurred")
        }
    }

    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRow: Int = repository.update(subscriber)
        if (noOfRow > 0){
            inputEmail.value = null
            inputName.value = null
            isUpdateOrDelete = false

            saveOrUpdateButtonText.value = "Save"
            clearOrDeleteButtonText.value = "Delete all"
            statusMessage.value = Event("$noOfRow row update successfully")
        }else {
            statusMessage.value = Event("Error occurred while updating")
        }
    }

    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val numberOfDeletedRow = repository.delete(subscriber)
        if (numberOfDeletedRow > 0){
            inputEmail.value = null
            inputName.value = null
            isUpdateOrDelete = false

            saveOrUpdateButtonText.value = "Save"
            clearOrDeleteButtonText.value = "Delete all"
            statusMessage.value = Event("$numberOfDeletedRow deleted successfully")
        }else {
            statusMessage.value = Event("Error occurred while deleting $numberOfDeletedRow")
        }
    }

    private fun deleteAll() = viewModelScope.launch {
        val deleteStatus: Int = repository.deleteAll()
        if (deleteStatus > 0 ){
            statusMessage.value = Event("$deleteStatus subscribers deleted successfully")
        }else {
            statusMessage.value = Event("Error occurred while deleting")
        }
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