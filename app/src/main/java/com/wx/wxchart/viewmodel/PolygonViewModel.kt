package com.wx.wxchart.viewmodel

import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wx.chart.data.PolygonBean
import com.wx.chart.data.PolygonModel
import com.wx.wxchart.MyApp

class PolygonViewModel : ViewModel() {
    private val _datas = MutableLiveData<PolygonModel>()
    val polygonModel: LiveData<PolygonModel> = _datas

    fun setData() {
        _datas.value = PolygonModel(
            5, //背景层N变形从小到大个数
            mutableListOf(
                PolygonBean("应用层UI", 95f),
                PolygonBean("Framework", 81f),
                PolygonBean("性能优化", 94f),
                PolygonBean("NDK音视频", 88f),
                PolygonBean("架构", 80f),
//                PolygonBean("魔法", 58f),
//                PolygonBean("装备", 78f),
//                PolygonBean("血量", 98f),
//                PolygonBean("政治", 75f),
//                PolygonBean("生物", 89f),
//                PolygonBean("地理", 69f),
//                PolygonBean("篮板", 89f),
//                PolygonBean("篮板", 89f),

            ), 100f,//满分多少
            toDp(80f), //最大半径偏移
            color = Color(0x30EEAA00),//背景填充颜色
            valueColor = Color(0x50FF0000),//值填充颜色
            valueLineColor = Color(0xFFFF0000)//值外层线条颜色
        )
    }

    fun toDp(size: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, MyApp.application.resources.displayMetrics)
}