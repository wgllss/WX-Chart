package com.wx.chart.data

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

data class ChatLineBean(
    val listY: MutableList<Float>,//点 集
    @Stable val color: Color,//曲线颜色
    val lineTitle: String //曲线名称 比如 收藏曲线 点赞曲线
) {
    val maxY: Float
        get() {
            var max = 0f
            listY.forEach {
                it.takeIf {
                    it > max
                }?.let {
                    max = it
                }
            }
            return max
        }
}