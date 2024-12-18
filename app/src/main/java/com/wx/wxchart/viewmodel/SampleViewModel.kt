package com.wx.wxchart.viewmodel

import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wx.chart.data.ChartBarBean
import com.wx.chart.data.ChartBarModel
import com.wx.chart.data.ChartPieBean
import com.wx.chart.data.ChartPieModel
import com.wx.chart.data.ChatLineBean
import com.wx.chart.data.ChatLineModel
import com.wx.wxchart.MyApp
import kotlin.random.Random

class SampleViewModel : ViewModel() {
    private val _datas = MutableLiveData<ChatLineModel>()
    val chatLineModel: LiveData<ChatLineModel> = _datas

    private val _datas2 = MutableLiveData<ChartBarModel>()
    val chatBarModel: LiveData<ChartBarModel> = _datas2

    private val _datas3 = MutableLiveData<ChartPieModel>()
    val chatPieModel: LiveData<ChartPieModel> = _datas3

    private val _datas4 = MutableLiveData<ChartPieModel>()
    val chatHoopModel: LiveData<ChartPieModel> = _datas4

    private val _datas5 = MutableLiveData<ChartBarModel>()
    val chatHBarModel: LiveData<ChartBarModel> = _datas5

    fun setData() {

        val chatLineBean1 = ChatLineBean(getRandomList(), Color.Red, "收藏")
        val chatLineBean2 = ChatLineBean(getRandomList(), Color.Magenta, "点赞") //曲线数据，曲线颜色
        val chatLineBean3 = ChatLineBean(getRandomList(), Color.Blue, "评论")//曲线数据，曲线颜色
        val chatLineBean4 = ChatLineBean(getRandomList(), Color.Green, "阅读")//曲线数据，曲线颜色
        val chatmodel = ChatLineModel(
            datas = mutableListOf(chatLineBean1, chatLineBean2, chatLineBean3, chatLineBean4), //多条曲线集
            listX = getXlables(), //x轴上刻度文字
        ).apply {
            yCount = 5 //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
            xCount = getXlables().size //x轴纵向上点数 包含 0 一般为X数据集size 必须大于1
            offsetx = toDp(100f) //UI上原点左下角 x偏移
            offsetxLable = toDp(32f)//原点 y轴上面刻度文字x偏移  相对控件最左边偏移
            offsety = toDp(30f) //UI上原点左下角 y 偏移
            offsetyLable = toDp(8f)//原点 y上面刻度文字文字 y偏移 相对控件左下角点,调整Y值文字在竖直中间位置 与横线对齐
            xLableStep = 4 //x轴上刻度对应文字，太多了显示不下，可以设置显示步长。如隔4个显示一个
            isShowYLine = false//是否显示Y轴线
            clickLayerColor = Color(0x30000000) //点击后展示浮层背景颜色
        }
        _datas.value = chatmodel
    }

    fun setData2() {
        //条形图数据
        val chatBarModel = ChartBarModel(getRandomBarList()).apply {
            barWidth = toDp(15f)
            yCount = 5 //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
            xCount = getXlables().size //x轴纵向上点数 包含 0 一般为X数据集size 必须大于1
            offsetx = toDp(100f) //UI上原点左下角 x偏移
            offsetxLable = toDp(32f)//原点 y轴上面刻度文字x偏移  相对控件最左边偏移
            offsety = toDp(30f) //UI上原点左下角 y 偏移
            offsetyLable = toDp(8f)//原点 y上面刻度文字文字 y偏移 相对控件左下角点,调整Y值文字在竖直中间位置 与横线对齐
            xLableStep = 2 //x轴上刻度对应文字，太多了显示不下，可以设置显示步长。如隔4个显示一个
            isShowYLine = true//是否显示Y轴线
            clickLayerColor = Color(0x90000000) //点击后展示浮层背景颜色
            layerWidth = toDp(100f)
        }
        _datas2.value = chatBarModel
    }

    fun setData3() {
        //圆饼图数据
        val chartPieModel = ChartPieModel(getRandomPieList(), toDp(50f)).apply {
            offsetx = toDp(-80f) //UI上圆心偏移X ,负数向做移动
            offsetxLable = toDp(150f)//右边文字距离 圆形右边距离偏移量
            offsety = 0f //UI圆心 y 偏移，负数 向上移动
            durationMillis = 1000
            lableLeftOffetx = toDp(60f)
        }
        _datas3.value = chartPieModel
    }


    fun setData4() {
        //环型图数据
        val chartHoopModel = ChartPieModel(getRandomPieList(), toDp(100f)).apply {
            offsetx = toDp(-80f) //UI上圆心偏移X ,负数向做移动
            offsetxLable = toDp(150f)//右边文字距离 圆形右边距离偏移量
            offsety = 0f //UI圆心 y 偏移，负数 向上移动
            durationMillis = 1000
            isHoop = true
            lableLeftOffetx = toDp(60f)
            hoopSize = toDp(80f)
        }
        _datas4.value = chartHoopModel
    }

    fun setData5() {
        //条形图
        val chatHBarModel = ChartBarModel(getRandomBarList()).apply {
            barWidth = toDp(15f)
            offextBarTopY = toDp(5f)
            offextBarBottomY = toDp(5f)
            yCount = list.size //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
            xCount = 10 //X轴横线刻度数 ，包含 0，比如0到5 设置为 6
            offsetx = toDp(72f) //UI上原点左下角 x偏移
            offsetxLable = toDp(20f)//原点 y轴上面刻度文字x偏移  相对控件最左边偏移
            offsety = toDp(30f) //UI上原点左下角 y 偏移
            offsetyLable = toDp(8f)//原点 y上面刻度文字文字 y偏移 相对控件左下角点,调整Y值文字在竖直中间位置 与横线对齐
            layerWidth = toDp(100f)
        }
        _datas5.value = chatHBarModel
    }

    fun getRandomList(): MutableList<Float> {
        val list = mutableListOf<Float>()
        for (i in 0..9) {
            list.add(Random.nextInt(5362).toFloat())
        }
        return list
    }

    fun getXlables(): MutableList<String> {
        val listXLable = mutableListOf<String>()
        var xt = 20241101
        for (i in 0..9) {
            listXLable.add("${xt}")
            xt += 1
        }
        return listXLable
    }

    fun getRandomBarList(): MutableList<ChartBarBean> {
        val list = mutableListOf<ChartBarBean>()
        val listColor = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta, Color.Yellow, Color.Cyan, Color.Gray)
        for (i in 0..9) {
            list.add(ChartBarBean(Random.nextInt(5362).toFloat(), listColor[Random.nextInt(listColor.size)], "城市${i}"))
        }
        return list
    }

    fun getRandomPieList(): MutableList<ChartPieBean> {
        val list = mutableListOf<ChartPieBean>()
        val listColor = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta, Color.Yellow, Color.Cyan, Color.Gray, Color(0xF0EFD5A0), Color(0xFFF0FD00), Color(0xFACE5DA0))
        for (i in 0..9) {
            list.add(ChartPieBean("城市${i}", Random.nextInt(5362).toFloat(), listColor[i]))
        }
        return list
    }

    fun toDp(size: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, MyApp.application.resources.displayMetrics)
}