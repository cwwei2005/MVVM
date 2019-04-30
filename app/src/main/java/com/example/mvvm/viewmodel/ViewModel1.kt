package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.MyRepository
import com.example.mvvm.model.net.NetState

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