package com.wx.wxchart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wx.compose1.ui.data.MainUIItem
import com.wx.wxchart.activity.BarChartActivity
import com.wx.wxchart.activity.HoopChartActivity
import com.wx.wxchart.activity.LineChartActivity
import com.wx.wxchart.activity.LineChartActivity2
import com.wx.wxchart.activity.PieChartActivity
import kotlinx.coroutines.launch

class ComposeViewModel : ViewModel() {

    private val _mainItems = MutableLiveData<List<MainUIItem>>(listOf())
    val mainItmes: LiveData<List<MainUIItem>> = _mainItems


    fun initUIData() {
        viewModelScope.launch {
            val list = listOf(
                MainUIItem("折线图表", LineChartActivity2::class.java), MainUIItem("贝塞尔曲线表", LineChartActivity::class.java), MainUIItem("柱状图表", BarChartActivity::class.java), MainUIItem("圆饼图表", PieChartActivity::class.java), MainUIItem("圆环图", HoopChartActivity::class.java)
            )
            _mainItems.value = list
        }
    }
}