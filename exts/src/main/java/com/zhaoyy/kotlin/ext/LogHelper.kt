package com.zhaoyy.kotlin.ext

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log

/**
 * LogHelper for Kotlin.
 * by ZHAO YY
 * on 2019/8/6.
 */
object LogHelper {

  private var isDebuggable: Boolean = false

  private val flag = "${LogHelper::class.java.simpleName}.kt"

  fun initDebuggable(context: Context) {
    context.applicationInfo?.let {
      isDebuggable = it.flags and ApplicationInfo.FLAG_DEBUGGABLE > 0
    }
  }

  private fun createLog(log: String): Pair<String, String> {
    val element: StackTraceElement = Throwable().stackTrace.first {
      flag != it.fileName
    }
    return Pair(
        "[${element.fileName}#${element.methodName}]",
        "(${element.fileName}:${element.lineNumber})\n$log"
    )
  }

  fun log(
    type: Int,
    msg: String
  ) {

    if (!isDebuggable) return

    val pair = createLog(msg)
    val tag = pair.first
    val logMsg = pair.second
    when (type) {
      Log.VERBOSE -> Log.v(tag, logMsg)
      Log.DEBUG -> Log.d(tag, logMsg)
      Log.INFO -> Log.i(tag, logMsg)
      Log.WARN -> Log.w(tag, logMsg)
      Log.ERROR -> Log.e(tag, logMsg)
      else -> {
      }
    }
  }
}

fun loge(msg: String) = LogHelper.log(Log.ERROR, msg)

fun logd(msg: String) = LogHelper.log(Log.DEBUG, msg)
