package com.letty7.kotlinmvp.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.letty7.kotlinmvp.App
import com.letty7.kotlinmvp.R
import com.letty7.kotlinmvp.base.BaseActivity
import com.letty7.kotlinmvp.bean.Gank
import com.letty7.kotlinmvp.mvp.contract.MainContract
import com.letty7.kotlinmvp.mvp.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private val sTAG = MainActivity::class.java.simpleName

    private lateinit var mAdapter: MainAdapter

    private var mPage = 1

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun afterOnCreate(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        mAdapter = MainAdapter()

        recyclerView.apply {

            itemAnimator = DefaultItemAnimator()

            val gridLayoutManager = GridLayoutManager(this@MainActivity, 2)

            layoutManager = gridLayoutManager
            adapter = mAdapter

            addItemDecoration(GridItemDecoration(2, dp2px(6), true))

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalCount = mAdapter.itemCount
                    val lastVisibility = gridLayoutManager.findLastVisibleItemPosition()

                    if (totalCount - lastVisibility <= 3) {
                        mPage++
                        refreshLayout.isRefreshing = true
                        mPresenter?.getGank(mPage, MainContract.TYPE_MORE)
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            fab.show()
                            Glide.with(App.sContext).resumeRequests()
                        }
                        RecyclerView.SCROLL_STATE_DRAGGING -> {
                            fab.hide()
                            Glide.with(App.sContext).pauseRequests()
                        }
                        RecyclerView.SCROLL_STATE_SETTLING -> {
                            fab.hide()
                            Glide.with(App.sContext).resumeRequests()
                        }
                    }
                }
            })
        }

        fab.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post {
            mPresenter?.getGank(0, MainContract.TYPE_REFRESH)
        }

    }

    override fun onCreatePresenter(): MainPresenter = MainPresenter(this)

    override fun onRefresh() {
        mPage = 1
        mPresenter?.getGank(0, MainContract.TYPE_REFRESH)
    }

    override fun showDialog() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSucceed(gank: Gank, type: Int) {

        val datas: MutableList<Gank.Result> = ArrayList()

        gank.results.forEach {
            datas.add(it)
        }

        when (type) {
            MainContract.TYPE_REFRESH -> mAdapter.setDatas(datas)
            MainContract.TYPE_MORE -> mAdapter.addDatas(datas)
        }

    }

    override fun onFailure(string: String) {
        Log.e(sTAG, string)
    }

    override fun hideDialog() {
        refreshLayout.isRefreshing = false
    }

    private fun dp2px(dp: Int): Int {
        val scale = App.sContext.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

}
