package com.example.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.db.User
import com.example.mvvm.net.NetState

class ViewModel1 : ViewModel() {
    fun getEntitys(t:Class<out Any>):LiveData<out List<Any>>? {
        return MyRepository.getInstance().getEntitys(t)
    }

    fun getNetState():LiveData<NetState>? {
        return MyRepository.getInstance().getNetState()
    }

    fun refresh(t:Class<out Any>){
        MyRepository.getInstance().refresh(t)
    }
}