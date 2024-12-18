package com.wx.chart.data

import com.wx.chart.data.dynamic.DynamicBarBean

data class DynamicDrawData(
    val keyNo: Int, val max: Float, val list: MutableList<DynamicBarBean>
)
