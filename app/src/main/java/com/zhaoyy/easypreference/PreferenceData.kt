package com.zhaoyy.easypreference

import android.content.Context
import com.zhaoyy.easyproference.EasyPreference

/**
 * PreferenceData
 * by ZHAOYY
 * on 2017/6/29.
 */
class PreferenceData(val context: Context, val prefName: String) {
  var name: String by EasyPreference(context, prefName, "default string")
  var number: Float by EasyPreference(context, prefName, 0f)
}