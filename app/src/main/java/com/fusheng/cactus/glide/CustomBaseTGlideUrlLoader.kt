package com.fusheng.cactus.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.*
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream
import java.util.regex.Pattern

/**
 * @Description:
 * @Author:        李晓伟
 * @CreateDate:     2020/5/4 23:20
 */
class CustomBaseTGlideUrlLoader(
    concreateLoader: ModelLoader<GlideUrl, InputStream>,
    modelCache: ModelCache<String, GlideUrl>
) : BaseGlideUrlLoader<String>(concreateLoader, modelCache) {
    companion object {
        private val urlCache = ModelCache<String, GlideUrl>(150)
        private val PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__")
    }

    override fun getUrl(model: String, width: Int, height: Int, options: Options?): String {
        val m = PATTERN.matcher(model)
        var bestBucket: Int
        if (m.find()) {
            val found = m.group(1).split("-".toRegex()).dropLastWhile {
                it.isEmpty()
            }.toTypedArray()
            for (bucketStr in found) {
                bestBucket = Integer.parseInt(bucketStr)
                if (bestBucket >= width) {
                    break
                }
            }
        }
        return model
    }

    override fun handles(model: String): Boolean {
        return true
    }

    class Factory : ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return CustomBaseTGlideUrlLoader(
                multiFactory.build(GlideUrl::class.java, InputStream::class.java),
                urlCache
            )
        }

        override fun teardown() {
        }

    }
}