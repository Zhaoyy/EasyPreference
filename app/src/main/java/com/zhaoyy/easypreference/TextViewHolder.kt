package com.zhaoyy.easypreference

import android.view.Gravity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * TextViewHolder
 * by ZHAOYY
 * on 2017/7/5.
 */
class TextViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {

  init {
    textView.gravity = Gravity.CENTER
  }
}