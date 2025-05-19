package com.wx.wxchart.viewmodel

import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wx.chart.data.ChartBarBean
import com.wx.chart.data.ChartBarModel
import com.wx.chart.data.ChartMapModel
import com.wx.chart.data.ChartPieBean
import com.wx.chart.data.ChartPieModel
import com.wx.chart.data.ChatLineBean
import com.wx.chart.data.ChatLineModel
import com.wx.chart.utils.ParserXmlUtils
import com.wx.wxchart.MyApp
import com.wx.wxchart.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.random.Random

class MapViewModel : ViewModel() {
    private val _datas = MutableLiveData<ChartMapModel>()
    val mapData: LiveData<ChartMapModel> = _datas

    private val _mapList = MutableLiveData<MutableList<Int>>()
    val mapList: LiveData<MutableList<Int>> = _mapList

    fun setData() {
        viewModelScope.launch {
            flow {
                emit(getMap(R.raw.chinahigh))
            }.flowOn(Dispatchers.IO).catch { it.printStackTrace() }.collect {
                _datas.value = it
            }
        }
    }

    fun setData2() {
        viewModelScope.launch {
            flow {

                val list = mutableListOf(
//                    WXData.getDituDrawable("四川"),
//                    WXData.getDituDrawable("江苏"),
                    R.drawable.xinjiang,
                    R.drawable.gansu,
                    R.drawable.qinghai,
                    R.drawable.neimenggu,
                    R.drawable.ningxia,
                    R.drawable.shanxi,
                    R.drawable.shanxi2,
                    R.drawable.hebei,
                    R.drawable.henan,
                    R.drawable.hubei,
                    R.drawable.hainan,
                    R.drawable.hunan,
                    R.drawable.chongqing,
                    R.drawable.guizhou,
                    R.drawable.yunnan,
                    R.drawable.guangxi,
                    R.drawable.guangdong,
                    R.drawable.fujian,
                    R.drawable.jiangxi,
                    R.drawable.zhejiang,
                    R.drawable.shanghai,
                    R.drawable.anhui,
                    R.drawable.shandong,
                    R.drawable.taiwan,
                    R.drawable.xianggang,
                    R.drawable.aomen,
                    R.drawable.beijing,
                    R.drawable.tianjing,
                    R.drawable.heilongjiang,
                    R.drawable.jilin,
                    R.drawable.xizang,
                    R.drawable.sichuan,
                    R.drawable.jiangsu,
                    R.drawable.liaoning,
                    R.drawable.chinahigh,
                )
                emit(list)
            }.flowOn(Dispatchers.IO).catch { it.printStackTrace() }.collect {
                _mapList.value = it
            }
        }
    }

    fun setData3() {
        viewModelScope.launch {
            flow {
                emit(getMap(R.raw.sichuan))
            }.flowOn(Dispatchers.IO).catch { it.printStackTrace() }.collect {
                _datas.value = it
            }
        }
    }

    private fun getMap(id: Int) = ParserXmlUtils.parserXml(MyApp.application.resources, id, listColor = listColor)

    private val listColor = listOf(
        Color(0xFF00FFFF),
        Color(0xFFFF0000),
        Color(0xFF00FF00),
        Color(0xFF0000FF),
        Color(0xFFFF00FF),
        Color(0xFFFFFF00),
        Color(0xFF888888),
        Color(0xF0EFD5A0),
        Color(0xFFF0FD00),
        Color(0xFACE5DA0),
        Color(0xFF03DAC5),
        Color(0xFFFFCC00),
        Color(0xFFFF99CC),
        Color(0xFFCCCC00),
        Color(0xFF99CC99),
        Color(0xFF999933),
        Color(0xFF993366),
        Color(0xFF006600),
        Color(0xFF330000),
        Color(0xFF339999),
        Color(0xFFCC0000),
        Color(0xFFCC3399),
        Color(0xFFFF9966),
        Color(0xFFCCFF33),
        Color(0xFFCCCCFF),
        Color(0xFF99CCCC),
        Color(0xFFFFCCCC),
        Color(0xFFCCCC33),
        Color(0xFFFF3333),
        Color(0xFF6633CC),
        Color(0xFFFFFF33),
        Color(0xFF00CC00),
        Color(0xFF996600),
        Color(0xFF990099),
    )
}