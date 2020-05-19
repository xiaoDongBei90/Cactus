package com.fusheng.cactus.mvp.mode.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 17:09
 */
class TabEntity(
    var title: String,
    private var selectedIcon: Int,
    private var unSelectedIcon: Int
) :
    CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }

}