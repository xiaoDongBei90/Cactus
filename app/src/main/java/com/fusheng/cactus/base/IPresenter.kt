package com.fusheng.cactus.base

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 19:00
 */
interface IPresenter<in V : IBaseView> {
    fun attachView(mRootView: V)
    fun detachView()
}