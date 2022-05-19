package com.shofiq.roomexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shofiq.roomexample.db.Subscriber
import com.shofiq.roomexample.repo.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel() {

    val subscribers = repository.subscribers
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()


    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insertSubscriber(Subscriber(0,name,email))
        inputEmail.value = null
        inputName.value = null
    }
    fun clearOrDelete(){

    }

    fun insertSubscriber(subscriber: Subscriber)  = viewModelScope.launch {
            repository.insert(subscriber)
        }

    fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.update(subscriber)
    }
    fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}