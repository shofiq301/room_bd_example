package com.shofiq.roomexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shofiq.roomexample.databinding.ActivityMainBinding
import com.shofiq.roomexample.db.Subscriber
import com.shofiq.roomexample.db.SubscriberDatabase
import com.shofiq.roomexample.repo.SubscriberRepository
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: SubscriberRecyclerAdapter
    private lateinit var subscribers: List<Subscriber>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)

        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.subscriberViewModel = this.subscriberViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        subscriberViewModel.message.observe(this, Observer{
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerSubscriber.layoutManager = LinearLayoutManager(this)
        subscribers = ArrayList<Subscriber>()
        adapter = SubscriberRecyclerAdapter { selectedItem: Subscriber ->
            listItemCLicked(
                selectedItem
            )
        }
        binding.recyclerSubscriber.adapter = adapter

        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscribers.asLiveData().observe(this, Observer {
            lifecycleScope.launch {
                adapter.setData(it)
            }
        })
    }

    private fun listItemCLicked(subscriber: Subscriber){
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}