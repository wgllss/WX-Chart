package com.wx.chart.data

class ChartPieModel(val list: MutableList<ChartPieBean>, val radiusOffset: Float) : ChartBaseModel() {

    var hoopSize: Float = 50f
    var isHoop: Boolean = false
    var scaleOffset: Float = 50f
    var lableLeftOffetx: Float = 50f

    val total: Float
        get() {
            var total = 0.00f
            list.forEach {
                total += it.value
            }
            return total
        }
}