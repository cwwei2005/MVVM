package com.example.mvvm.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseBindingAdapter<T>(private val layoutId:Int, private val BRid:Int): RecyclerView.Adapter<BaseBindingAdapter<T>.MyViewHolder>() {

    private val list: MutableList<T> = mutableListOf()
    private var mBinding: ViewDataBinding? = null
    private var headerView: View? = null
    private var footerView: View? = null
    private var hasHeader = false
    private var hasFooter = false

    var onItemClick: ((pos:Int, t:T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        mBinding = binding
        val viewHolder = MyViewHolder(binding.root)
        viewHolder.setBinding(binding)
        Log.e("tag","onCreateViewHolder")
        return viewHolder
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.getBinding().setVariable(BRid, list[position])
        holder.getBinding().executePendingBindings()
    }


    fun refresh(mode: REFRESH_TYPE, list: List<T>) {
        if (mode == REFRESH_TYPE.REFRESH) this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private lateinit var binding:ViewDataBinding
        fun setBinding(binding:ViewDataBinding){
            this.binding = binding
        }
        fun getBinding(): ViewDataBinding{
            return this.binding
        }
    }


    enum class REFRESH_TYPE {
        LOAD_MORE,
        REFRESH,
    }

}