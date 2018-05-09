package com.letty7.kotlinmvp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<P : BasePresenter<*, *>> : AppCompatActivity() {

    protected var mPresenter: P? = null

    abstract fun getLayoutId() : Int

    abstract fun afterOnCreate(savedInstanceState: Bundle?)

    protected abstract fun onCreatePresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() == 0) {
            throw UnsupportedOperationException("Please set layout in \"getLayoutId()\".")
        }
        setContentView(getLayoutId())
        if (mPresenter == null) {
            mPresenter = onCreatePresenter()
        }
        afterOnCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unDisposable()
    }

}