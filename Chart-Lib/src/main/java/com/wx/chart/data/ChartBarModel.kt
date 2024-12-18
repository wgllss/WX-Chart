package com.wx.chart.data

class ChartBarModel(val list: MutableList<ChartBarBean>) : ChartBarBaseModel() {
    val maxY: Float
        get() {
            var max = 0f
            list.forEach {
                it.value.takeIf {
                    it > max
                }?.let {
                    max = it
                }
            }
            return max
        }
}