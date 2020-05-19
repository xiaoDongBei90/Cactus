package com.fusheng.cactus.view.recyclerview

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 19:23
 */
interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}