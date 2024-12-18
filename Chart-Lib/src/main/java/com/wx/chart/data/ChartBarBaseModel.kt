package com.wx.chart.data

open class ChartBarBaseModel : ChartLineBarBaseModel() {
    var barWidth: Float = 40f
    var isTop: Boolean = false
    var offextBarTopY = 0f  //顶部第一条bar 距离顶部距离 ，Y轴线距离顶部距离
    var offextBarBottomY = 0f //顶部第一条bar 距离底部距离 Y轴线距离底部距离
    var offextBarRightX = 0f //X轴距离右边距离
    var offextBarLeftX = 0f //X轴距离左边距离
}