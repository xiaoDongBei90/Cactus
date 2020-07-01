package com.fusheng.cactus.mvp.mode

import com.fusheng.cactus.mvp.mode.bean.HomeBean
import io.reactivex.Observable

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/6/2 23:13
 */
class HomeModel {
    fun requestHomeData(num:Int):Observable<HomeBean>{
        return Retro
    }
}