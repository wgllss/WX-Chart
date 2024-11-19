package com.wx.wxchart

import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wx.chart.data.ChatLineBean
import com.wx.chart.data.ChatModel
import kotlin.random.Random

class SampleViewModel : ViewModel() {
    private val _datas = MutableLiveData<ChatModel>()
    val chatModel: LiveData<ChatModel> = _datas

    fun setData() {

        val chatLineBean1 = ChatLineBean(getRandomList(), Color.Red, "收藏")
        val chatLineBean2 = ChatLineBean(getRandomList(), Color.Magenta, "点赞") //曲线数据，曲线颜色
        val chatLineBean3 = ChatLineBean(getRandomList(), Color.Blue, "评论")//曲线数据，曲线颜色
        val chatLineBean4 = ChatLineBean(getRandomList(), Color.Green, "阅读")//曲线数据，曲线颜色
        val chatmodel = ChatModel(
            datas = mutableListOf(chatLineBean1, chatLineBean2, chatLineBean3, chatLineBean4), //多条曲线集
            listX = getXlables(), //x轴上刻度文字
            yCount = 5, //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
            xCount = getXlables().size, //x轴纵向上点数 包含 0 一般为X数据集size 必须大于1
            offsetx = toDp(72f), //UI上原点左下角 x偏移
            offsetxLable = toDp(12f),//原点 y轴上面刻度文字x偏移  相对控件最左边偏移
            offsety = toDp(30f), //UI上原点左下角 y 偏移
            offsetyLable = toDp(8f),//原点 y上面刻度文字文字 y偏移 相对控件左下角点,调整Y值文字在竖直中间位置 与横线对齐
            xLableStep = 2, //x轴上刻度对应文字，太多了显示不下，可以设置显示步长。如隔4个显示一个
            isShowYLine = false,//是否显示Y轴线
            clickLayerColor = Color(0x30000000) //点击后展示浮层背景颜色
        )


        _datas.value = chatmodel
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

    fun toDp(size: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, MyApp.application.resources.displayMetrics)
}