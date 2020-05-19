package com.fusheng.cactus.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.fusheng.cactus.view.recyclerview.MultipleType
import com.fusheng.cactus.view.recyclerview.ViewHolder

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 19:20
 */
abstract class CommonAdapter<T>(
    var context: Context, var data: ArrayList<T>,
    private var layoutId: Int
) : RecyclerView.Adapter<ViewHolder>() {
    protected var inflater: LayoutInflater? = null
    private var typeSupport: MultipleType<T>? = null
    private var itemClickListener: OnItemClickListener? = null
    private var itemLongClickListener: OnItemLongClickListener? = null

    init {
        inflater = LayoutInflater.from(context)
    }

    constructor(context: Context, data: ArrayList<T>, typeSupport: MultipleType<T>)
            : this(context, data, -1) {
        this.typeSupport = typeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (typeSupport != null) {
            layoutId = viewType
        }
        val view = inflater?.inflate(layoutId, parent, false)
        return ViewHolder(view!!)
    }

    override fun getItemViewType(position: Int): Int {
        return typeSupport?.getLayoutId(data[position], position) ?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindData(holder, data[position], position)
        itemClickListener?.let {
            holder.itemView.setOnClickListener {
                itemClickListener?.onItemClick(data[position], position)
            }
        }
        itemLongClickListener?.let {
            holder.itemView.setOnLongClickListener {
                itemLongClickListener!!.onItemLongClick(data[position], position)
            }
        }
    }

    override fun getItemCount(): Int = data.size
    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener
    }

    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)
}