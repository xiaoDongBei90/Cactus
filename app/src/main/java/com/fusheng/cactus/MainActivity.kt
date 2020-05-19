package com.fusheng.cactus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.fusheng.cactus.base.BaseActivity
import com.fusheng.cactus.mvp.mode.bean.TabEntity
import com.fusheng.cactus.ui.fragment.DiscoveryFragment
import com.fusheng.cactus.ui.fragment.HomeFragment
import com.fusheng.cactus.ui.fragment.HotFragment
import com.fusheng.cactus.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

/**
 * @Description:
 * @Author:         李晓伟
 * @CreateDate:     2020/5/3 22:55
 */
class MainActivity : BaseActivity() {
    private val titles = arrayOf("每日精选", "发现", "热门", "我的")
    private var homeFragment: HomeFragment? = null
    private var discoveryFragment: DiscoveryFragment? = null
    private var hotFragment: HotFragment? = null
    private var mineFragment: MineFragment? = null
    private var tabIndex = 0
    private val iconUnSelectIds = intArrayOf(
        R.mipmap.ic_home_normal,
        R.mipmap.ic_discovery_normal,
        R.mipmap.ic_hot_normal,
        R.mipmap.ic_mine_normal
    )
    private val iconSelectedIds = intArrayOf(
        R.mipmap.ic_home_selected,
        R.mipmap.ic_discovery_selected,
        R.mipmap.ic_hot_selected,
        R.mipmap.ic_mine_selected
    )
    private val tabEntities = ArrayList<CustomTabEntity>()

    override fun layoutId(): Int = R.layout.activity_main

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            tabIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab = tabIndex
        switchFragment(tabIndex)
    }

    private fun initTab() {
        (0 until titles.size).mapTo(tabEntities) {
            TabEntity(titles[it], iconSelectedIds[it], iconUnSelectIds[it])
        }
        tab_layout.setTabData(tabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    private fun switchFragment(position: Int) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        hideFragment(beginTransaction)
        when (position) {
            0 -> homeFragment?.let {
                beginTransaction.show(it)
            } ?: HomeFragment.getInstance(titles[position]).let {
                homeFragment = it
                beginTransaction.add(R.id.fl_container, it, "home")
            }
            1 -> discoveryFragment?.let {
                beginTransaction.show(it)
            } ?: DiscoveryFragment.getInstance(titles[position])
                .let {
                    discoveryFragment = it
                    beginTransaction.add(R.id.fl_container, it, "discovery")
                }
            2 -> hotFragment?.let {
                beginTransaction.show(it)
            } ?: HotFragment.getInstance(titles[position])
                .let {
                    hotFragment = it
                    beginTransaction.add(R.id.fl_container, it, "hot")
                }
            3 -> mineFragment?.let {
                beginTransaction.show(it)
            } ?: MineFragment.getInstance(titles[position]).let {
                mineFragment = it
                beginTransaction.add(R.id.fl_container, it, "mine")
            }
        }
        tabIndex = position
        tab_layout.currentTab = tabIndex
        beginTransaction.commitAllowingStateLoss()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        discoveryFragment?.let { transaction.hide(it) }
        hotFragment?.let { transaction.hide(it) }
        mineFragment?.let { transaction.hide(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (tab_layout != null) {
            outState.putInt("currTabIndex", tabIndex)
        }
    }

    private var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus((exitTime)) <= 2000) {
                finish()
            } else {
                exitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
