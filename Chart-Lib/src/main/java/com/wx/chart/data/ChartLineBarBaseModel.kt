package com.wx.chart.data

open class ChartLineBarBaseModel : ChartBaseModel() {
    var yCount: Int = 6 //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
    var xCount: Int = 10//x轴纵向上点数 包含 0 一般为X数据集size
    var xLableStep: Int = 3  //xLable 上太多了显示不下，可以设置显示步长。如隔2个显示一个
    var isShowYLine: Boolean = true//是否显示Y轴线
}
