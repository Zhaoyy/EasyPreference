package com.zhaoyy.kotlin.ext

import android.util.Log

/**
 * Ext
 * by ZHAOYY
 * on 2019/4/16.
 */
inline fun <reified T> T.logd(msg: String) = Log.d(T::class.java.simpleName, msg)

inline fun <reified T> T.loge(msg: String) = Log.e(T::class.java.simpleName, msg)