package com.fusheng.cactus.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.fusheng.cactus.CactusApp
import com.fusheng.cactus.MainActivity
import com.fusheng.cactus.R
import com.fusheng.cactus.base.BaseActivity
import com.fusheng.cactus.utils.AppUtils
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.jar.Manifest

/**
 * @Description:
 * @Author:         李晓伟
 * @CreateDate:     2020/5/3 22:55
 */
class SplashActivity : BaseActivity() {
    private var textTypeface: Typeface? = null
    private var descTypeface: Typeface? = null
    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeface = Typeface.createFromAsset(CactusApp.context.assets, "fonts/Lobster-1.4.otf")
        descTypeface = Typeface.createFromAsset(
            CactusApp.context.assets,
            "fonts/FZLanTingHeiS-L-GB-Regular.TTF"
        )
    }

    override fun layoutId(): Int = R.layout.activity_splash

    override fun initData() {

    }

    override fun initView() {
        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeface
        tv_version_name.text = "v${AppUtils.getVersionName(CactusApp.context)}"

        //启动动画
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 1500
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                goToMain()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        checkPermission()
    }

    override fun start() {
    }

    fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun checkPermission() {
        val params = arrayOf(
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        EasyPermissions.requestPermissions(this, "仙人掌需要以下权限，请您允许", 0, *params)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        super.onPermissionsGranted(requestCode, perms)
        if (requestCode == 0
            && perms.isNotEmpty()
            && perms.contains(android.Manifest.permission.READ_PHONE_STATE)
            && perms.contains(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            && alphaAnimation != null
        ) {
            iv_splash_icon.startAnimation(alphaAnimation)
        }
    }
}