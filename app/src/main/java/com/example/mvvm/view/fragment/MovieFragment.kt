package com.example.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.BR
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentMovieBinding
import com.example.mvvm.model.db.Theater
import com.example.mvvm.model.db.Top250
import com.example.mvvm.model.net.NetState
import com.example.mvvm.view.adapter.BaseBindingAdapter
import com.example.mvvm.viewmodel.MyViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.fragment_movie.*

@BindingMethods(BindingMethod(type = ImageView::class, attribute = "app:imageUrl", method = "setImageUrl"))
class MovieFragment(val type:Int) : Fragment() {

    private var inited:Boolean = false
    private var binding: FragmentMovieBinding? = null
    private var vm: MyViewModel? = null
    private var adapter: BaseBindingAdapter<Any>? = null
    private var page: Int = 0  //分页获取
    private val COUNT: Int = 10  //每页数量
    private var refreshType: BaseBindingAdapter.REFRESH_TYPE = BaseBindingAdapter.REFRESH_TYPE.REFRESH

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null){
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("tag","type: $type" )
        if (!inited){
            inited = true
            initBindingViewModel()
            initView()
            initData()
        }
    }

    private fun initBindingViewModel(){
        vm = ViewModelProviders.of(this).get(MyViewModel::class.java)
        binding!!.vm3 = vm
//        binding?.isLoading3 = true
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = when(type){
            1 -> BaseBindingAdapter<Any>(R.layout.recyclerview_movie_zhengzai, BR.movie)
            2 -> BaseBindingAdapter<Any>(R.layout.recyclerview_movie_jijiang, BR.movie1)
            3 -> BaseBindingAdapter<Any>(R.layout.recyclerview_movie_top250, BR.movie2)
            else -> null
        }
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.setLoadingListener(object:XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                refreshType = BaseBindingAdapter.REFRESH_TYPE.LOAD_MORE
                loadMore()
                Log.e("tag","load more...")
            }

            override fun onRefresh() {
                refreshType = BaseBindingAdapter.REFRESH_TYPE.REFRESH
                refresh()
                Log.e("tag","refresh...")
            }
        })
    }

    private fun initData(){
        val observer = Observer<List<Any>> { t ->
            updateUI(t)
            Log.e("tag","data size: ${t.size}")
        }

        val netStatObserver = Observer<NetState> { stat ->
            binding!!.isLoading3 = false
            Log.e("tag","netState: $stat")
        }

        /**只要调用过MyDatabase.getInstance().theaterDao().getAll()，以后每次操作这个表observer都能观察到变化**/
        vm?.getNetState()?.observe(this, netStatObserver)
        when(type){
            1 -> vm?.getEntitysInPage(Theater::class.java, "广州", page, COUNT)?.observe(this, observer)
            2 -> vm?.getEntitys(Top250::class.java)?.observe(this, observer)
            3 -> vm?.getEntitys(Top250::class.java)?.observe(this, observer)
            else -> null
        }
    }


    private fun loadMore(){
        when(type){
            1 -> vm?.getEntitysInPage(Theater::class.java, "广州", ++page, COUNT)
            2 -> vm?.getEntitysInPage(Top250::class.java, "广州", ++page, COUNT)
            3 -> vm?.getEntitysInPage(Top250::class.java, "广州", ++page, COUNT)
            else -> null
        }
        Log.e("tag","page: $page")
    }

    private fun refresh(){
        when(type){
            1 -> vm?.getEntitysInPage(Theater::class.java, "广州", 0, COUNT)
            2 -> vm?.getEntitysInPage(Top250::class.java, "广州", 0, COUNT)
            3 -> vm?.getEntitysInPage(Top250::class.java, "广州", 0, COUNT)
            else -> null
        }
        page = 0
        Log.e("tag","page: $page")
    }

    private fun updateUI(t: List<Any>){
        when(type){
            1 -> { val list = t as List<Theater>; adapter?.refresh(refreshType, list[0].subjects!!) }
            2 -> { adapter?.refresh(refreshType, t as List<Top250>) }
            3 -> { adapter?.refresh(refreshType, t as List<Top250>) }
            else -> null
        }
        tvLoading3.visibility = View.GONE
        if (refreshType == BaseBindingAdapter.REFRESH_TYPE.REFRESH) binding?.recyclerView?.refreshComplete()  //停止refresh
        else if (refreshType == BaseBindingAdapter.REFRESH_TYPE.LOAD_MORE) binding?.recyclerView?.loadMoreComplete()  //停止loading
    }

}
