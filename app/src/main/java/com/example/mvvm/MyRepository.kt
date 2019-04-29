package com.example.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mvvm.db.MyDatabase
import com.example.mvvm.db.User

class MyRepository{

    companion object {
        @Volatile private var intence: MyRepository? = null
        fun getInstance(): MyRepository{
            if (intence == null){
                synchronized(MyRepository::class.java){
                    if (intence == null){
                        intence = MyRepository()
                    }
                }
            }
            return intence!!
        }
    }


    //
//    fun getUsers(): LiveData<List<User>>{
//        return if (false){
//            //net
//            MyDatabase.getInstance().userDao().getUsers()
//        } else{
//            MyDatabase.getInstance().userDao().getUsers()
//        }
//    }

    fun getEntitys(t : Class<out Any>): LiveData<out List<Any>>?{
        return if (false){
            //net
            return null
        } else{
            when(t.newInstance()){  //注意：传递进来的类需要有构造函数（data class 默认是没有构造函数，需要自己添加）
                is User -> return MyDatabase.getInstance().userDao().getUsers()  //不需要在线程读
                else -> null
            }
        }
    }
}