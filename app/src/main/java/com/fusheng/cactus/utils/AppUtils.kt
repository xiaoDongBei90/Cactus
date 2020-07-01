package com.fusheng.cactus.utils

import android.content.Context
import android.os.Build

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 13:12
 */
class AppUtils {
    companion object {
        fun getVersionName(context: Context): String {
            val packageName = context.packageName
            return context.packageManager.getPackageInfo(packageName, 0).versionName
        }


        fun getMobileModel(): String {
            var model: String? = Build.MODEL
            model = model?.trim { it <= ' ' } ?: ""
            return model
        }
    }


}