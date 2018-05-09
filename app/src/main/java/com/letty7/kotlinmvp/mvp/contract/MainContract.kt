package com.letty7.kotlinmvp.mvp.contract

import com.letty7.kotlinmvp.base.BaseModel
import com.letty7.kotlinmvp.base.BasePresenter
import com.letty7.kotlinmvp.base.BaseView
import com.letty7.kotlinmvp.bean.Gank
import io.reactivex.Observable

interface MainContract {

    companion object {
        const val TYPE_REFRESH = 0
        const val TYPE_MORE = 1
    }

    interface View : BaseView {

        fun showDialog()

        fun onSucceed(gank: Gank, type: Int)

        fun onFailure(string: String)

        fun hideDialog()
    }


    interface Model : BaseModel {

        fun getData(page: Int): Observable<Gank>

    }

    abstract class Presenter : BasePresenter<View, Model>() {
        abstract fun getGank(page: Int, type: Int)
    }

}