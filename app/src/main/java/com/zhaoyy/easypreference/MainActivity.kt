package com.zhaoyy.easypreference

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

  private lateinit var data: PreferenceData

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    data = PreferenceData(applicationContext, prefName)

    data.name = "test name"
    data.number = 20F

    Log.e(TAG, "name = ${data.name}")
    Log.e(TAG, "number = ${data.number}")
  }

  companion object {
    val prefName = "preference"
    val TAG = MainActivity::class.java.simpleName
  }
}
