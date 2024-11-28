package com.wx.chart.data

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

data class ChartPieBean(val name: String, val value: Float, @Stable val color: Color)
