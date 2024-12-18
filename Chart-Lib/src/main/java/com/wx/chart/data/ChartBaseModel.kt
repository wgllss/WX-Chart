package com.wx.chart.data

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

open class ChartBaseModel {
    var offsetx: Float = 10f//原点 x偏移
    var offsetxLable: Float = 0f//原点 y轴上面刻度文字偏移
    var offsety: Float = 10f //原点 y 偏移
    var offsetyLable: Float = 10f //原点 y上面刻度文字文字 偏移


    var layerWidth: Float = 2 * offsetx//
    var durationMillis: Int = 1000 // 动画时长
    var animateDelay: Long = 1000 //动画延迟执行时间

    @Stable
    var clickLayerColor: Color = Color(0x80000000)//点击后展示浮层背景颜色

    @Stable
    var colorXLine: Color = Color.Black // X轴线颜色

    @Stable
    var colorYLine: Color = Color.Black //Y 轴线颜色

    @Stable
    var colorLine: Color = Color.LightGray //刻度线颜色，可能竖线，也可能是横线，看图标

    @Stable
    var colotLable: Color = Color.Black
}
