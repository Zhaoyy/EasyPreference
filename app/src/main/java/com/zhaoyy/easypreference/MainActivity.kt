package com.zhaoyy.easypreference

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.State
import com.alibaba.android.vlayout.VirtualLayoutAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper
import com.alibaba.android.vlayout.layout.FixLayoutHelper
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper
import com.alibaba.android.vlayout.layout.StickyLayoutHelper
import kotlinx.android.synthetic.main.activity_main.btn_add_grid
import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity() {

  private lateinit var data: PreferenceData

  private lateinit var vAdapter: VirtualLayoutAdapter<TextViewHolder>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    testPreference()

    initRecyclerView()

    btn_add_grid.setOnClickListener {
      val count = vAdapter.itemCount
      val layoutHelpers = vAdapter.layoutHelpers.toMutableList()
      val lastHelper = layoutHelpers.last()
      lastHelper.itemCount = lastHelper.itemCount + 1

      vAdapter.layoutHelpers = layoutHelpers

      recycler_view.adapter?.notifyItemInserted(count)
    }
  }

  private fun initRecyclerView() {

    val vLayoutManager = VirtualLayoutManager(this)
    recycler_view.layoutManager = vLayoutManager

    recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
      override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: State
      ) {
        outRect.set(10, 10, 10, 10)
      }
    })

    val gridLayoutHelper = GridLayoutHelper(4, 10)
    val scrollFixLayoutHelper = ScrollFixLayoutHelper(FixLayoutHelper.TOP_RIGHT, 100, 100)
    scrollFixLayoutHelper.showType = ScrollFixLayoutHelper.SHOW_ON_ENTER

    // column layout helper
    val columnLayoutHelper = ColumnLayoutHelper()
    columnLayoutHelper.setWeights(floatArrayOf(40f, 30f, 15f, 10f, 5f))
    columnLayoutHelper.itemCount = 5

    val onePlusN = OnePlusNLayoutHelper(4)

    val stickLayout = StickyLayoutHelper(false)

    vLayoutManager.setLayoutHelpers(
        mutableListOf(
            DefaultLayoutHelper.newHelper(2), stickLayout, scrollFixLayoutHelper,
            DefaultLayoutHelper.newHelper(2), gridLayoutHelper, stickLayout, columnLayoutHelper,
            onePlusN
        )
    )

    vAdapter = object : VirtualLayoutAdapter<TextViewHolder>(vLayoutManager) {

      override fun onBindViewHolder(
        holder: TextViewHolder,
        position: Int
      ) {
        val layoutParam = VirtualLayoutManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            200
        )
        holder.textView.text = position.toString()

        if (position == 7) {
          layoutParam.height = 100
          layoutParam.width = 100
        }

        when {
          position > 35 -> holder.textView.setBackgroundColor(0x66cc0000 + (position - 30) * 128)
          position % 2 == 0 -> holder.textView.setBackgroundColor(Color.GREEN)
          else -> holder.textView.setBackgroundColor(Color.RED)
        }

        holder.textView.layoutParams = layoutParam
      }

      override fun getItemCount(): Int {
        var count = 0

        layoutHelpers.forEach {
          count += it.itemCount
        }

        return count
      }

      override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
      ): TextViewHolder {
        return TextViewHolder(TextView(parent.context))
      }

    }

    recycler_view.adapter = vAdapter
  }

  private fun testPreference() {
    data = PreferenceData(applicationContext, prefName)

    data.name = "test name"
    data.number = 20F

    Log.e(TAG, "name = ${data.name}")
    Log.e(TAG, "number = ${data.number}")
  }

  companion object {
    const val prefName = "preference"
    val TAG = MainActivity::class.java.simpleName
  }
}
