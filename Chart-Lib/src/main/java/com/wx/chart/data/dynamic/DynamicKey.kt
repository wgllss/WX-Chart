package com.wx.chart.data.dynamic

data class DynamicKey(
    val currentKey: String,    //当前数据的年份 或者日期，或者其他作为K的值
    val maxValue: Float,       //当前K值下最大的值
    val xMaxValue: Float,      //当前x坐标轴上面最大的刻度数值，比如
)
