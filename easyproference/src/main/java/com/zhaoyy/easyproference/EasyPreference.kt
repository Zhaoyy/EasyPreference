package com.zhaoyy.easyproference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlin.reflect.KProperty

/**
 * EasyPreference
 * by ZHAOYY
 * on 2017/6/29.
 */
class EasyPreference<T>(val context: Context, val preferenceName: String, val default: T) {

  init {
    pref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
  }

  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    Log.e(TAG, "set ${property.name} = $value")
    put(property.name, value)
  }

  operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
    val value = get(property.name)
    Log.e(TAG, "get ${property.name} = $value")
    return value
  }

  private fun put(name: String, value: T?) = with(pref.edit()) {
    when (value) {
      value == null -> remove(name)
      is Long -> putLong(name, value)
      is String -> putString(name, value)
      is Int -> putInt(name, value)
      is Boolean -> putBoolean(name, value)
      is Float -> putFloat(name, value)
      else -> throw IllegalArgumentException("SharedPreferences can't be save this type")
    }.apply()
  }

  @Suppress("UNCHECKED_CAST")
  private fun get(name: String): T = with(pref) {
    val res: Any = when (default) {
      is Long -> getLong(name, default)
      is String -> getString(name, default)
      is Int -> getInt(name, default)
      is Boolean -> getBoolean(name, default)
      is Float -> getFloat(name, default)
      else -> throw IllegalArgumentException("SharedPreferences can't be get this type")
    }
    return res as T
  }

  companion object {
    lateinit var pref: SharedPreferences
    val TAG = EasyPreference::class.java.simpleName
  }
}