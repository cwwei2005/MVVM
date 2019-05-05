package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.MyRepository
import com.example.mvvm.model.net.NetState

class MyViewModel : ViewModel() {
//    fun getEntity(t:Class<out Any>):LiveData<out List<Any>>? {
//        return MyRepository.getInstance().getEntity(t)
//    }

    fun getEntitys(t:Class<out Any>):LiveData<out List<Any>>? {
        return MyRepository.getInstance().getEntitys(t)
    }

    fun getEntitysInPage(t:Class<out Any>, city:String, page:Int, num:Int):LiveData<out List<Any>>? {
        return MyRepository.getInstance().getEntitysInPage(t, city, page, num)
    }

    fun getNetState():LiveData<NetState>? {
        return MyRepository.getInstance().getNetState()
    }


    fun refresh(t:Class<out Any>){
        MyRepository.getInstance().refresh(t)
    }
}