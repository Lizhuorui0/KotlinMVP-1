package com.letty7.kotlinmvp.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.letty7.kotlinmvp.App
import com.letty7.kotlinmvp.R
import com.letty7.kotlinmvp.bean.Gank

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var mDatas: MutableList<Gank.Result>? = null

    fun setDatas(datas: MutableList<Gank.Result>) {

        if (datas.isEmpty()) return

        mDatas = datas
        notifyDataSetChanged()
    }

    fun addDatas(datas: MutableList<Gank.Result>) {

        if (datas.isEmpty()) return

        val oldSize = mDatas?.size as Int
        mDatas?.addAll(datas)
        val newSize = mDatas?.size as Int
        notifyItemRangeInserted(oldSize, newSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = if (mDatas == null) 0 else mDatas?.size as Int

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = mDatas?.get(position)

        Glide.with(App.sContext).load(data?.url).into(holder.imageView)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

}