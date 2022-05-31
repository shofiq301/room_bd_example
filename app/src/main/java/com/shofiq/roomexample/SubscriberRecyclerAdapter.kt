package com.shofiq.roomexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shofiq.roomexample.databinding.ListItemBinding
import com.shofiq.roomexample.db.Subscriber
import com.shofiq.roomexample.generated.callback.OnClickListener

class SubscriberRecyclerAdapter(private val clickListener: (Subscriber) -> Unit): RecyclerView.Adapter<SubscriberViewHolder>() {
    private var subscribers = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.list_item, parent, false)
        return SubscriberViewHolder(binding as ListItemBinding)
    }

    fun setData(newList: List<Subscriber>){
//        val diffUtil = MyDiffUtil(newList, subscribers)
//        val diffResult = DiffUtil.calculateDiff(diffUtil)
        subscribers.clear()
        subscribers.addAll(newList)
        notifyDataSetChanged()
//        diffResult.dispatchUpdatesTo(this)
//        subscribers = newList as ArrayList<Subscriber>
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribers[position],clickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }
}
class SubscriberViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
        binding.subscriberItem = subscriber
        binding.content.setOnClickListener {
            clickListener(subscriber)
        }
    }
}