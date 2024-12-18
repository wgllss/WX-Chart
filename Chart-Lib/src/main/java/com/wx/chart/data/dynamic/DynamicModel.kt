package com.wx.chart.data.dynamic

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.wx.chart.data.ChartBarBaseModel
import com.wx.chart.data.DynamicKey

class DynamicModel(
    val arrayKey: Array<DynamicKey>, //存放当前年份特定值，当前年份Key,
    val mapList: MutableMap<String, MutableList<DynamicBarBean>>,//数据集，通过当前年份key查找
    val mapImage: MutableMap<String, DynamicImage> //每个条形图的动态图标，可配置网络图片
) : ChartBarBaseModel() {

    var dynamicChartName = "" //动态表名称

    @Stable
    var titleColor: Color? = null//每个条形名称颜色

    var bgUrl: String = ""//整个动画背景图

    var keyTextOffsetX = 0f//年份相对右下角X便宜
    var keyTextOffextY = 0f//年份相对右下角Y便宜
    var maxValueOffsetRight = 0f//该值为条形右边文字说明所占有的宽度，因为条形最大长度 = X轴线长度-条形右边文字说明所占有的宽度
    var musicUrl = ""//背景音乐，可配置网络链接
    var formatString: String = ""//数字格式化设置
    var multiplier: Float = 1f//数据显示格式所用的乘数
    var isPlayComplete = false
    var isAutoBarWidth = false//是否自动计算条形宽度


    fun maxValue(keyPos: Int) = arrayKey[keyPos].maxValue

    fun getTextValueFormat(value: Float): String {
        return formatString?.format(value * multiplier) ?: value.toString()
    }
}