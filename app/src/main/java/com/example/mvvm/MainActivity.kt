package com.example.mvvm

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.db.Article
import com.example.mvvm.db.MyDatabase
import com.example.mvvm.db.Theater
import com.example.mvvm.db.User
import com.example.mvvm.net.NetState
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var vm: ViewModel1? = null
    private var adapter: BaseBindingAdapter<Theater>? = null  //不同类需要修改

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindingViewModel()
        initView()
        initData()
    }


    private fun initBindingViewModel(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = ViewModelProviders.of(this).get(ViewModel1::class.java)
        binding!!.vm = vm
    }

    private fun initView(){
        initRecyclerView()
        initButton()
    }

    private fun initRecyclerView() {
        adapter = BaseBindingAdapter<Theater>(R.layout.recyclerview_theater_item, BR.theater)  //不同类需要修改
        binding!!.recyclerView.layoutManager = LinearLayoutManager(this)
        binding!!.recyclerView.adapter = adapter
    }

    private fun initButton() {
        binding!!.button1.setOnClickListener {
//            Thread(Runnable { MyDatabase.getInstance().userDao().insert(User(null, "cww")) }).start()
        }
        binding!!.button2.setOnClickListener {
            Thread(Runnable {
                val theaters = MyDatabase.getInstance().theaterDao().getAll2()
                if (theaters.isEmpty()) return@Runnable
                MyDatabase.getInstance().theaterDao().delete(theaters[0])
            }).start()
        }
        binding!!.button3.setOnClickListener {
            Thread(Runnable {
                val theaters = MyDatabase.getInstance().theaterDao().getAll2()
                if (theaters.isEmpty()) return@Runnable
                theaters[theaters.size-1].title = "beijing bei jing"
                MyDatabase.getInstance().theaterDao().update(theaters[theaters.size-1])
            }).start()
        }
        binding!!.button4.setOnClickListener {
            binding!!.isLoading = true
            vm?.refresh(Theater::class.java)
        }
    }

    private fun initData(){
        val observer = Observer<List<Any>> { t ->
            val list = t as List<Theater>  //不同类需要修改
            adapter?.refresh(list)
            Log.e("tag","data size: ${list.size}")
        }

        val observerNetStat = Observer<NetState> { stat ->
            binding!!.isLoading = false
//            Log.e("tag","netState: $stat")
        }

        vm?.getEntitys(Theater::class.java)?.observe(this, observer)  /**只要调用过MyDatabase.getInstance().theaterDao().getAll()，以后每次操作这个表observer都能观察到变化**/
        vm?.getNetState()?.observe(this, observerNetStat)
        binding!!.isLoading = true
    }
}
