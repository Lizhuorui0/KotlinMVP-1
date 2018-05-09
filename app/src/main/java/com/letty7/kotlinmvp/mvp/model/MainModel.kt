package com.letty7.kotlinmvp.mvp.model

import com.letty7.kotlinmvp.api.ApiEngine
import com.letty7.kotlinmvp.bean.Gank
import com.letty7.kotlinmvp.mvp.contract.MainContract
import com.letty7.kotlinmvp.rx.RxSchedulers
import io.reactivex.Observable

class MainModel : MainContract.Model {

    override fun getData(page: Int): Observable<Gank> {
        return ApiEngine.getApiService()
                .getData(page)
                .compose(RxSchedulers.switchThread())
    }
}
