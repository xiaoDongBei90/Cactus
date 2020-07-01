package com.fusheng.cactus.base

import io.reactivex.disposables.CompositeDisposable
import java.lang.RuntimeException

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/5 21:54
 */
open class BasePresenter<T : IBaseView> : IPresenter<T> {
    var mRootView: T? = null
    private var compositeDisposable = CompositeDisposable()

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) {
            throw  MvpViewNotAttachedException()
        }
    }

    private class MvpViewNotAttachedException internal constructor() :
        RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}