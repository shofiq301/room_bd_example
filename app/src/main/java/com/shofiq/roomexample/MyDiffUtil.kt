package com.shofiq.roomexample

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.shofiq.roomexample.db.Subscriber

class MyDiffUtil(private val oldList: List<Subscriber>, private val newList: List<Subscriber>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSubscriber = oldList[oldItemPosition]
        val newSubscriber = newList[newItemPosition]
        return (oldSubscriber.name == newSubscriber.name) && (oldSubscriber.email == newSubscriber.email)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val diffBundle = Bundle()
        diffBundle.putInt(oldList[oldItemPosition].id.toString(), newList[newItemPosition].id)
        return diffBundle
    }
}