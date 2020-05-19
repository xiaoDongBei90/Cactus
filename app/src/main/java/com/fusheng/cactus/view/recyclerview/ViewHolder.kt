package com.fusheng.cactus.view.recyclerview

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 19:30
 */
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var views: SparseArray<View>? = null

    init {
        views = SparseArray()
    }

    fun <T : View> getView(viewId: Int): T {
        var view: View? = views?.get(viewId)
        if (view == null) {
            view = itemView.findViewById<T>(viewId)
            views?.put(viewId, view)
        }
        return view as T
    }

    fun <T : ViewGroup> getViewGroup(viewId: Int): T {
        var view: View? = views?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views?.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.text = "" + text
        return this
    }

    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): ViewHolder {
        val iv = getView<ImageView>(viewId)
        imageLoader.loadImage(iv, imageLoader.path)
        return this
    }

    abstract class HolderImageLoader(val path: String) {
        abstract fun loadImage(iv: ImageView, path: String)
    }

    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    fun setOnItemClickListener(listener: View.OnClickListener) {
        itemView.setOnClickListener(listener)
    }

    fun setOnItemLongClickListener(listener: View.OnLongClickListener) {
        itemView.setOnLongClickListener(listener)
    }
}