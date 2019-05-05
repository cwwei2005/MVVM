package com.example.mvvm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityMain2Binding
import com.example.mvvm.model.db.Top250
import com.example.mvvm.model.net.NetState
import com.example.mvvm.view.adapter.BaseBindingAdapter
import com.example.mvvm.view.adapter.BaseFragmentPagerAdapter
import com.example.mvvm.view.fragment.MovieFragment
import com.example.mvvm.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    private var binding: ActivityMain2Binding? = null
    private var vm: MyViewModel? = null
    private var adapter: BaseBindingAdapter<Top250>? = null  //不同类需要修改

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindingViewModel()
        initView()
//        initData()
    }


    private fun initBindingViewModel(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        vm = ViewModelProviders.of(this).get(MyViewModel::class.java)
        binding!!.vm2 = vm
    }

    private fun initView(){
        initViewPager()
    }

    private fun initViewPager() {
        val fragments = listOf(MovieFragment(1), MovieFragment(2), MovieFragment(3))
        val tabs = listOf("正在上映", "即将上映", "top250")
        val adapter = BaseFragmentPagerAdapter(supportFragmentManager, fragments, tabs)
        viewPager.adapter = adapter
//        viewPager.offscreenPageLimit = 2
    }

//    private fun initData(){
//        val observer = Observer<List<Any>> { t ->
//            val list = t as List<Top250>  //不同类需要修改
//            adapter?.refresh(list)
//            Log.e("tag","data size: ${list.size}")
//        }
//
//        val observerNetStat = Observer<NetState> { stat ->
//            binding!!.isLoading2 = false
//            Log.e("tag","netState: $stat")
//        }
//
//        vm?.getEntitys(Top250::class.java)?.observe(this, observer)  /**只要调用过MyDatabase.getInstance().theaterDao().getAll()，以后每次操作这个表observer都能观察到变化**/
//        vm?.getNetState()?.observe(this, observerNetStat)
//        binding!!.isLoading2 = true
//    }
}
