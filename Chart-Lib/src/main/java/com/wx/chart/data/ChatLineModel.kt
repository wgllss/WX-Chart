package com.wx.chart.data

data class ChatLineModel(
    val datas: MutableList<ChatLineBean>,//多条曲线集
    val listX: MutableList<String>,//x轴上刻度文字
) : ChartBaseModel() {
    val maxY: Float
        get() {
            var max = 0f
            datas.forEach {
                it.maxY.takeIf {
                    it > max
                }?.let {
                    max = it
                }
            }
            return max
        }
}