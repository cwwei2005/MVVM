package com.example.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.db.User

class ViewModel1 : ViewModel() {

//    fun getUsers():LiveData<List<User>> {
//        return MyRepository.getInstance().getUsers()
//    }

    fun getEntitys(t:Class<out Any>):LiveData<out List<Any>>? {
        return MyRepository.getInstance().getEntitys(t)
    }
}