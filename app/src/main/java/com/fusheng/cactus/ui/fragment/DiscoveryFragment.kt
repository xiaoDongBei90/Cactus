package com.fusheng.cactus.ui.fragment

import android.os.Bundle
import com.fusheng.cactus.base.BaseFragment

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 16:58
 */
class DiscoveryFragment : BaseFragment() {
    private var title: String? = null
    override fun getLayoutId(): Int = 0

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }
}