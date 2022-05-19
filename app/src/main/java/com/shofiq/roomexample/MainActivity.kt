package com.shofiq.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.shofiq.roomexample.databinding.ActivityMainBinding
import com.shofiq.roomexample.db.SubscriberDatabase
import com.shofiq.roomexample.repo.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)

        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.subscriberViewModel = this.subscriberViewModel
        binding.lifecycleOwner = this
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscribers.asLiveData().observe(this, Observer {
            Log.i("MY_DATA", it.toString())
        })
    }
}