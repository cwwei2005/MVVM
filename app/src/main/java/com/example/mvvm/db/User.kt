package com.example.mvvm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true) val id:Int?,
    var name:String){
    constructor() : this(1, "")
}