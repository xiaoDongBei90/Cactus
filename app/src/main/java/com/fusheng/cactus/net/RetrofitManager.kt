package com.fusheng.cactus.net

import android.preference.Preference
import com.fusheng.cactus.CactusApp
import com.fusheng.cactus.api.UrlConstant
import com.fusheng.cactus.utils.AppUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/6/2 23:15
 */
object RetrofitManager {
    private var token: String by Preference("token", "")

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(UrlConstant.BASE_URL)
            .client()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val cacheFile = File(CactusApp.context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50)
        return OkHttpClient.Builder()
            .addInterceptor()
    }

    /**
     * 公共参数设置
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                .addQueryParameter("deviceModel", AppUtils.getMobileModel())
                .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 请求头设置
     */
    private fun addHeaderInterceptor():Interceptor{
        return Interceptor {
            chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .header("token",token)
        }
    }
}