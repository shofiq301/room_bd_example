package com.shofiq.roomexample.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriberDataTable")
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriberId")
    var id: Int,

    @ColumnInfo(name = "subscriberName")
    var name: String,

    @ColumnInfo(name = "subscriberEmail")
    var email: String
)