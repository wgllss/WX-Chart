package com.wx.chart.data

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

open class ChartBaseModel {
    var yCount: Int = 6 //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
    var xCount: Int = 10//x轴纵向上点数 包含 0 一般为X数据集size
    var offsetx: Float = 10f//原点 x偏移
    var offsetxLable: Float = 0f//原点 y轴上面刻度文字偏移
    var offsety: Float = 10f //原点 y 偏移
    var offsetyLable: Float = 10f //原点 y上面刻度文字文字 偏移
    var xLableStep: Int = 3  //xLable 上太多了显示不下，可以设置显示步长。如隔2个显示一个
    var isShowYLine: Boolean = true//是否显示Y轴线
    var layerWidth: Float = 2 * offsetx//
    var durationMillis: Int = 1000 // 动画时长
    var animateDelay: Long = 200 //动画延迟执行时间

    @Stable
    var clickLayerColor: Color = Color(0x80000000)//点击后展示浮层背景颜色
}
