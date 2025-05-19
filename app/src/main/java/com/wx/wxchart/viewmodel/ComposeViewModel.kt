package com.wx.wxchart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wx.compose1.ui.data.MainUIItem
import com.wx.wxchart.activity.BarChartActivity
import com.wx.wxchart.activity.DrawMap2Activity
import com.wx.wxchart.activity.DrawMap3Activity
import com.wx.wxchart.activity.DrawMapActivity
import com.wx.wxchart.activity.DynamicBarChartActivity
import com.wx.wxchart.activity.DynamicTopBarActivity
import com.wx.wxchart.activity.HBarChartActivity
import com.wx.wxchart.activity.HoopChartActivity
import com.wx.wxchart.activity.LineChartActivity
import com.wx.wxchart.activity.LineChartActivity2
import com.wx.wxchart.activity.PK2Activity
import com.wx.wxchart.activity.PK3Activity
import com.wx.wxchart.activity.PK4Activity
import com.wx.wxchart.activity.PKActivity
import com.wx.wxchart.activity.PieChartActivity
import com.wx.wxchart.activity.PolygonActivity
import com.wx.wxchart.activity.Pyramid2Activity
import com.wx.wxchart.activity.Pyramid3Activity
import com.wx.wxchart.activity.PyramidActivity
import kotlinx.coroutines.launch

class ComposeViewModel : ViewModel() {

    private val _mainItems = MutableLiveData<List<MainUIItem>>(listOf())
    val mainItmes: LiveData<List<MainUIItem>> = _mainItems


    fun initUIData() {
        viewModelScope.launch {
            val list = listOf(
                MainUIItem("折线图表", LineChartActivity2::class.java),
                MainUIItem("贝塞尔曲线表", LineChartActivity::class.java),
                MainUIItem("柱状图表", BarChartActivity::class.java),
                MainUIItem("条形图表", HBarChartActivity::class.java),
                MainUIItem("圆饼图表", PieChartActivity::class.java),
                MainUIItem("圆环图", HoopChartActivity::class.java),
                MainUIItem("动态趋势图", DynamicBarChartActivity::class.java),
                MainUIItem("地图", DrawMapActivity::class.java),
                MainUIItem("地图2", DrawMap2Activity::class.java),
                MainUIItem("地图3", DrawMap3Activity::class.java),
                MainUIItem("动态排名可视化", DynamicTopBarActivity::class.java),
                MainUIItem("六边形绘制", PolygonActivity::class.java),
                MainUIItem("PK", PKActivity::class.java),
                MainUIItem("PK2", PK2Activity::class.java),
                MainUIItem("PK3", PK3Activity::class.java),
                MainUIItem("PK4", PK4Activity::class.java),
                MainUIItem("金字塔", PyramidActivity::class.java),
                MainUIItem("金字塔2", Pyramid2Activity::class.java),
                MainUIItem("金字塔3", Pyramid3Activity::class.java),
            )
            _mainItems.value = list
        }
    }
}