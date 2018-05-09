package com.letty7.kotlinmvp.mvp.presenter

import com.letty7.kotlinmvp.mvp.contract.MainContract
import com.letty7.kotlinmvp.mvp.model.MainModel

class MainPresenter(val view: MainContract.View) : MainContract.Presenter() {

    init {
        mView = view
        mModel = MainModel()
    }

    override fun getGank(page: Int,type : Int) {
        mView?.showDialog()
        mModel.getData(page)
                .subscribe({
                    mView?.onSucceed(it,type)
                    mView?.hideDialog()
                }, {
                    it.message?.let { it1 ->
                        mView?.onFailure(it1)
                    }
                    mView?.hideDialog()
                })
    }

}