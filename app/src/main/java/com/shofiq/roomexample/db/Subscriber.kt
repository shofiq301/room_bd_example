package com.shofiq.roomexample.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriberDataTable")
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriberId")
    val id: Int,

    @ColumnInfo(name = "subscriberName")
    val name: String,

    @ColumnInfo(name = "subscriberEmail")
    val email: String
)