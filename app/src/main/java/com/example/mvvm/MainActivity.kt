package com.example.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.db.MyDatabase
import com.example.mvvm.db.User

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var adapter: BaseBindingAdapter<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val vm: ViewModel1 = ViewModelProviders.of(this).get(ViewModel1::class.java)
        binding!!.vm1 = vm

        adapter = BaseBindingAdapter<User>(R.layout.recyclerview_item, BR.entity)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(this)
        binding!!.recyclerView.adapter = adapter
        binding!!.button1.setOnClickListener {
            Thread(Runnable { MyDatabase.getInstance().userDao().insert(User(null, "cww")) }).start()
        }
        binding!!.button2.setOnClickListener {
            Thread(Runnable {
                val users = MyDatabase.getInstance().userDao().getUsers2()
                if (users.isEmpty()) return@Runnable
                MyDatabase.getInstance().userDao().delete(users[0])
            }).start()
        }
        binding!!.button3.setOnClickListener {
            Thread(Runnable {
                val users = MyDatabase.getInstance().userDao().getUsers2()
                if (users.isEmpty()) return@Runnable
                users[0].name = "seven"
                MyDatabase.getInstance().userDao().update(users[0])
            }).start()
        }

//        vm.getUsers().observe(this, observer)
        vm.getEntitys(User::class.java)?.observe(this, observer1)
    }



//    private val observer = Observer<List<User>> { t ->
//        Log.e("tag","users: ${t?.size}")
//        adapter?.refresh(t)
//    }

    private val observer1 = Observer<List<Any>> { t ->
        val articles = t as List<User>
        Log.e("tag","Articles: ${t.size}")
        adapter?.refresh(t)
    }
}
