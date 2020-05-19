package com.fusheng.cactus

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.time.Duration

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 18:28
 */
fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(CactusApp.context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}