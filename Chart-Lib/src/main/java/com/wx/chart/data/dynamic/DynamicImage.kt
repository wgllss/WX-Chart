package com.wx.chart.data.dynamic

import androidx.compose.ui.graphics.ImageBitmap

data class DynamicImage(
    val imgUrl: String,  //每个条形图可配置的图标地址
    var bitmap: ImageBitmap //需要绘制的bitmap
)