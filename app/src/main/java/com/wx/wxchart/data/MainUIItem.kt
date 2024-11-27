package com.wx.compose1.ui.data

import androidx.activity.ComponentActivity

data class MainUIItem(
    val title: String, val clazz: Class<out ComponentActivity>
)
