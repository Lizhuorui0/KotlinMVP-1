package com.letty7.kotlinmvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V : BaseView, T : BaseModel> {

    protected var mView: V? = null
    protected lateinit var mModel: T

    private var mCompositeDisposable: CompositeDisposable? = null

    protected fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    fun unDisposable() {
        mView = null
        mCompositeDisposable?.clear()
    }

}