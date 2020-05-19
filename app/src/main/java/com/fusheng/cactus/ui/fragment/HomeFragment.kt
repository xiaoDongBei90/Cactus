package com.fusheng.cactus.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fusheng.cactus.R
import com.fusheng.cactus.base.BaseFragment
import com.fusheng.cactus.mvp.contract.HomeContract
import com.fusheng.cactus.mvp.mode.bean.HomeBean
import com.fusheng.cactus.ui.adapter.HomeAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.logging.Logger

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 13:45
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    private var title: String? = null
    private var homeAdapter: HomeAdapter? = null
    private var loadingMore = false
    private var isRefresh = false
    private var materialHeader: MaterialHeader? = null
    private var num: Int = 1
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }

    override fun setHomeData(homeBean: HomeBean) {
        layoutStatusView?.showContent()
        com.orhanobut.logger.Logger.d(homeBean)
        homeAdapter = activity?.let { HomeAdapter(it, homeBean.issueList[0].itemList) }
        homeAdapter?.setBannerSize(homeBean.issueList[0].count)
        rv.adapter = homeAdapter
        rv.layoutManager = LinearLayoutManager(activity)
        rv.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {

    }

    override fun showError(msg: String, errorCode: Int) {
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
    }
}