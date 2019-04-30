package com.example.mvvm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.db.User

class BaseBindingAdapter<T>(private val layoutId:Int, private val BRid:Int): RecyclerView.Adapter<BaseBindingAdapter<T>.MyViewHolder>() {

    private val list: MutableList<T> = mutableListOf()
//    private lateinit var binding: ViewDataBinding
    private var headerView: View? = null
    private var footerView: View? = null
    private var hasHeader = false
    private var hasFooter = false

    var onItemClick: ((pos:Int, t:T) -> Unit)? = null


    //利用返回类型添加头和尾部（下拉刷新和上拉加载）
//    override fun getItemViewType(position: Int): Int {
//        if (hasHeader){
//            if (position == 0) return ITEM_TYPE.HEADER.ordinal
//            else if (hasFooter && position == this.list.size+1) return ITEM_TYPE.FOOTER.ordinal
//        }
//        else if (hasFooter && position == this.list.size) return ITEM_TYPE.FOOTER.ordinal
//        return ITEM_TYPE.NORMAL.ordinal
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        if (viewType == ITEM_TYPE.HEADER.ordinal){
//            return MyViewHolder(headerView!!)
//        } else if (viewType == ITEM_TYPE.FOOTER.ordinal){
//            return MyViewHolder(footerView!!)
//        }
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        val viewHolder = MyViewHolder(binding.root)
        viewHolder.setBinding(binding)
        return viewHolder
    }


    override fun getItemCount(): Int {
        var count = list.size
//        if (hasHeader) count++
//        if (hasFooter) count++
        return count
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        if (list.size == 0) return
//        if (hasHeader && position == 0) return
//        if (hasFooter && position == itemCount-1) return

//        var pos = position
//        if (hasHeader) pos = position - 1
//        val ret = binding.setVariable(BRid, list[pos])
//        binding.root.setOnClickListener { onItemClick?.invoke(pos, list[pos]) }
//        binding.executePendingBindings()
//        binding.setVariable(BRid, list[position])
//        val obj = list[position] as User
//        binding.user?.name = obj.name
//        binding.executePendingBindings()
        holder.getBinding().setVariable(BRid, list[position])
        holder.getBinding().executePendingBindings()
    }


    fun refresh(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addHeadView(view:View){
        headerView = view
        hasHeader = true
    }

    fun addFootView(view:View){
        footerView = view
        hasFooter = true
    }



    fun setHeaderViewHeight(h:Int){
        headerView?.layoutParams?.height = h
//        headerView?.invalidate()
    }




    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        init {
//            if (itemView == headerView){
//                headerView?.layoutParams?.height = 0
//                headerView
//            }
//        }
        private lateinit var binding:ViewDataBinding
        fun setBinding(binding:ViewDataBinding){
            this.binding = binding
        }
        fun getBinding(): ViewDataBinding{
            return this.binding
        }
    }


    internal enum class ITEM_TYPE {
        HEADER,
        FOOTER,
        NORMAL
    }

}