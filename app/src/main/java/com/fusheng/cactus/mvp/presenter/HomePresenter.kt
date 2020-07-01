package com.fusheng.cactus.mvp.presenter

import com.fusheng.cactus.base.BasePresenter
import com.fusheng.cactus.mvp.contract.HomeContract

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/5 21:53
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    override fun requestHomeData(num: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable=homem
    }

    override fun loadMoreData() {

    }
}