package com.fusheng.cactus.ui.fragment

import android.os.Bundle
import com.fusheng.cactus.base.BaseFragment

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 16:59
 */
class HotFragment : BaseFragment() {
    private var title: String? = null
    override fun getLayoutId(): Int = 0

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }
}