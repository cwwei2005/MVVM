package com.example.mvvm

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.db.Article
import com.example.mvvm.db.MyDatabase
import com.example.mvvm.db.Theater
import com.example.mvvm.db.User
import com.example.mvvm.net.MyRetrofit
import com.example.mvvm.net.NetState
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.function.Consumer

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


    /**
     * 1、从网络获取实体对象，Any类型
     * 2、获取成功则写入数据库
     * 3、网络获取无论成功或失败都读取数据库并通知activity
     */
    fun getEntitys(t: Class<out Any>): LiveData<out List<Any>>? = getNetData(t)

    /**
     * 网络请求时，更新对应的网络状态
     */
    private val netState = MutableLiveData<NetState>()
    fun getNetState(): LiveData<NetState>? = netState

    /**
     * 刷新网络数据
     * 插入新数据或覆盖旧数据，操作数据库时UI的observer会自动感知数据变化
     */
    fun refresh(t:Class<out Any>){
        when(t.newInstance()){
            is Theater -> MyRetrofit.getInstance().httpApi.getTheaters()
            else -> null
        }?.subscribeOn(Schedulers.io())
            ?.doOnNext { theater ->  //获得数据，先写入数据库(io线程)，请求成功才执行doOnNext
                when(t.newInstance()){
                    is Theater -> MyDatabase.getInstance().theaterDao().replaceInsert(theater)
                    else -> null
                }
            }
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                netState.postValue(NetState.SUCCESS)
            }, { throwable->  //网络异常的处理
                netState.postValue(NetState.FAILED)
            })
    }



    private fun getNetData(t : Class<out Any>): LiveData<out List<Any>>?{
        val data = MediatorLiveData<List<Any>>()
        var dbData: LiveData<out List<Any>>? = null

        when(t.newInstance()){
            is Theater -> MyRetrofit.getInstance().httpApi.getTheaters()
            else -> null
        }?.subscribeOn(Schedulers.io())
//            ?.observeOn(Schedulers.io())
            ?.doOnNext { theater ->  //获得数据，先写入数据库(io线程)，请求成功才执行doOnNext
                when(t.newInstance()){
                    is Theater -> MyDatabase.getInstance().theaterDao().replaceInsert(theater)
                    else -> null
                }
                dbData = getDBData(t)
            }
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                notificationUIThread(dbData, data)
                netState.postValue(NetState.SUCCESS)
            }, { throwable->  //网络异常的处理。下面的代码：io线程读出数据，切换到主线程通知更新
                Observable.just(1)
                    .subscribeOn(Schedulers.io())
                    .doOnNext{ dbData = getDBData(t) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { notificationUIThread(dbData, data) }
                netState.postValue(NetState.FAILED)
            })

        return data
    }

    /**
     * 在主线程通知数据更新，这个是需要的：
     * 由于还没调用return MyDatabase.getInstance().theaterDao().getAll()，写入数据库还不能感知,
     * 需要调用一次，之后的数据库操作就可以感知到数据变化了.
     */
    private fun notificationUIThread(dbData: LiveData<out List<Any>>?, data: MediatorLiveData<List<Any>>){
        if (dbData != null){
            data.addSource(dbData) { list ->
//                if (dbData.value?.size!! > 0) data.postValue(list)
                data.postValue(list)  //主线程
            }
        }
    }

    private fun getDBData(t : Class<out Any>): LiveData<out List<Any>>?{
        return when(t.newInstance()){  //注意：传递进来的类需要有构造函数（如果是data class 默认是没有构造函数，需要自己添加）
            is User -> return MyDatabase.getInstance().userDao().getUsers()  //不需要在线程读
            is Article -> return MyDatabase.getInstance().articleDao().getAll()  //不需要在线程读
            is Theater -> return MyDatabase.getInstance().theaterDao().getAll()  //不需要在线程读
            else -> null
        }
    }
}