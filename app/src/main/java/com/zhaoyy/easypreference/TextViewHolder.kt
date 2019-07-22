package com.zhaoyy.easypreference

import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import android.widget.TextView

/**
 * TextViewHolder
 * by ZHAOYY
 * on 2017/7/5.
 */
class TextViewHolder(val textView: TextView) : androidx.recyclerview.widget.RecyclerView.ViewHolder(textView) {

  init {
    textView.gravity = Gravity.CENTER
  }
}