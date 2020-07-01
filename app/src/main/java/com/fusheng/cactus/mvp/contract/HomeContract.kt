package com.fusheng.cactus.mvp.contract

import com.fusheng.cactus.base.IBaseView
import com.fusheng.cactus.mvp.mode.bean.HomeBean

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 18:59
 */
interface HomeContract {
    interface View : IBaseView {
        fun setHomeData(homeBean: HomeBean)
        fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>)
        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter {
        fun requestHomeData(num: Int)
        fun loadMoreData()
    }
}