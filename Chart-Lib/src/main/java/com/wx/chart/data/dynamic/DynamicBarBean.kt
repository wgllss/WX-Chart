package com.wx.chart.data.dynamic

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

class DynamicBarBean(
    var value: Float,          //条形当前值
    @Stable val color: Color,  //条形颜色
    val title: String          //条形名称
) {
    var diffValue: Float = 0f  // 条形下一个值与当前值的差值
    var currPos: Int = 0 // 当前排名位置
    var nextPos: Int = 0 // 下一年份排名位置
    var diffPos: Int = 0 // nextPos - currPos
}