package com.fusheng.cactus.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fusheng.cactus.Constants
import com.fusheng.cactus.R
import com.fusheng.cactus.durationFormat
import com.fusheng.cactus.glide.GlideApp
import com.fusheng.cactus.mvp.mode.bean.HomeBean
import com.fusheng.cactus.ui.activity.VideoDetailActivity
import com.fusheng.cactus.view.recyclerview.ViewHolder
import com.fusheng.cactus.view.recyclerview.adapter.CommonAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_home_banner.view.*

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 19:13
 */
class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {
    var bannerItemSize = 0

    companion object {
        private const val ITEM_TYPE_BANNER = 1
        private const val ITEM_TYPE_TEXT_HEADER = 2
        private const val ITEM_TYPE_CONTENT = 3
    }

    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            data[position + bannerItemSize - 1].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return when {
            data.size > bannerItemSize -> data.size - bannerItemSize + 1
            data.isEmpty() -> 0
            else -> 1
        }
    }

    override fun bindData(holder: ViewHolder, d: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> =
                    data.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                Observable.fromIterable(bannerItemData)
                    .subscribe { list ->
                        bannerFeedList.add(list.data?.cover?.feed ?: "")
                        bannerTitleList.add(list.data?.title ?: "")
                    }

                with(holder){
                    getView<BGABanner>(R.id.bga_banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter { banner, _, feedImageUrl, position ->
                            GlideApp.with(context)
                                .load(feedImageUrl)
                                .transition(DrawableTransitionOptions().crossFade())
                                .placeholder(R.mipmap.placeholder_banner)
                                .into(banner.getItemImageView(position))


                        }
                    }
                }
                holder.getView<BGABanner>(R.id.bga_banner).setDelegate{
                    _,imageView,_,i->
                    goToVideoPlayer(context as Activity, imageView, bannerItemData[i])
                }
            }

            //TextHeader
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tv_header, data[position + bannerItemSize - 1].data?.text ?: "")
            }

            //content
            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder, data[position + bannerItemSize - 1])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER -> ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HEADER -> ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else -> ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }

    private fun inflaterView(layoutId: Int, parent: ViewGroup): View {
        val view = inflater?.inflate(layoutId, parent, false)
        return view ?: View(parent.context)
    }

    private fun setVideoItem(holder: ViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data
        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText = "#"
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        GlideApp.with(context)
            .load(cover)
            .placeholder(R.mipmap.placeholder_banner)
            .transition(DrawableTransitionOptions().crossFade())
            .into(holder.getView(R.id.iv_cover_feed))


        if (avatar.isNullOrEmpty()) {
            GlideApp.with(context)
                .load(defAvatar)
                .placeholder(R.mipmap.default_avatar)
                .circleCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_avatar))
        } else {
            GlideApp.with(context)
                .load(avatar)
                .placeholder(R.mipmap.default_avatar)
                .circleCrop()
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.getView(R.id.iv_avatar))
        }
        holder.setText(R.id.tv_title, itemData?.title ?: "")
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        val timeFormat = durationFormat(itemData?.duration)
        tagText += timeFormat
        holder.setText(R.id.tv_tag, tagText)
        holder.setText(R.id.tv_category, "#" + itemData?.category)
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(context as Activity, holder.getView(R.id.iv_cover_feed), item)
        })
    }

    /**
     * 跳转到视频详情页面播放
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                androidx.core.util.Pair.create(view, VideoDetailActivity.IMG_TRANSITION)
            )
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}