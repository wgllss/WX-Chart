package com.wx.wxchart.viewmodel

import android.text.TextUtils
import android.util.TypedValue
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wx.chart.data.dynamic.DynamicBarBean
import com.wx.chart.data.dynamic.DynamicImage
import com.wx.chart.data.dynamic.DynamicKey
import com.wx.chart.data.dynamic.DynamicModel
import com.wx.wxchart.MyApp
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class DynamicViewModel : ViewModel() {

    private val _datas = MutableLiveData<DynamicModel>()
    val dynamicModel: LiveData<DynamicModel> = _datas

    private val listColor = listOf(Color.Cyan, Color.Green, Color.Blue, Color.Magenta, Color.Yellow, Color.Red, Color.Gray, Color(0xF0EFD5A0), Color(0xFFF0FD00), Color(0xFACE5DA0))

    fun setData() {
        viewModelScope.launch {
            flow {
                val gson = Gson()
                val json = getFromAssets("txt/txt.txt")
                val dto = gson.fromJson<List<MutableMap<String, String>>>(json, object : TypeToken<List<MutableMap<String, String>>>() {}.type)

                val arrayKey = Array(dto[0].size - 4) { DynamicKey("", 0f, 0f) }
                val mapImage = mutableMapOf<String, DynamicImage>()
                val defaultBitmap = createImageBitmap()
                mapImage["美国"] = DynamicImage("https://gss0.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/8435e5dde71190ef3ab597f0c01b9d16fdfa6056.jpg", defaultBitmap)
                mapImage["日本"] = DynamicImage("https://bkimg.cdn.bcebos.com/smart/6a63f6246b600c338744433e9214460fd9f9d62a0bb6-bkimg-process,v_1,rw_1,rh_1,maxl_216,pad_1,color_ffffff?x-bce-process=image/format,f_auto", defaultBitmap)
                mapImage["德国"] = DynamicImage("https://pic.xcar.com.cn/img/news_photo/2010/06/21/gKWaAxcBN23773.jpg", defaultBitmap)
                mapImage["英国"] = DynamicImage("https://bkimg.cdn.bcebos.com/smart/cb8065380cd7912397dd8f558e6c4e82b2b7d0a22ac0-bkimg-process,v_1,rw_1,rh_1,maxl_216,pad_1,color_ffffff?x-bce-process=image/format,f_auto", defaultBitmap)
                mapImage["法国"] = DynamicImage("https://bkimg.cdn.bcebos.com/smart/63d9f2d3572c11dfa9ec31ad377475d0f703908f64bb-bkimg-process,v_1,rw_1,rh_1,maxl_216,pad_1,color_ffffff?x-bce-process=image/format,f_auto", defaultBitmap)
                mapImage["中国"] = DynamicImage("https://bkimg.cdn.bcebos.com/pic/d0c8a786c9177f3e67097eaf9c852cc79f3df8dcf874?x-bce-process=image/format,f_auto/resize,m_lfit,limit_1,h_1080", defaultBitmap)
                mapImage["意大利"] = DynamicImage("https://bkimg.cdn.bcebos.com/smart/562c11dfa9ec8a1363273ac34e4b868fa0ec08fa6af5-bkimg-process,v_1,rw_1,rh_1,maxl_216,pad_1,color_ffffff?x-bce-process=image/format,f_auto", defaultBitmap)
                mapImage["加拿大"] = DynamicImage("https://bkimg.cdn.bcebos.com/smart/a8773912b31bb05163d4e5cf3a7adab44bede061-bkimg-process,v_1,rw_1,rh_1,maxl_216,pad_1,color_ffffff?", defaultBitmap)
                mapImage["巴西"] = DynamicImage("https://img0.baidu.com/it/u=1359402126,1864975690&fm=253&fmt=auto&app=138&f=JPEG?w=384&h=269", defaultBitmap)
                mapImage["印度"] = DynamicImage("https://bkimg.cdn.bcebos.com/smart/f636afc379310a55b3194d878c1d54a98226cffcba32-bkimg-process,v_1,rw_1,rh_1,maxl_216,pad_1,color_ffffff?x-bce-process=image/format,f_auto", defaultBitmap)

                val mapList by lazy { mutableMapOf<String, MutableList<DynamicBarBean>>() }


                var keyNo = 1960
                while (keyNo <= 2023) {
                    doListByKey(dto, keyNo.toString(), mapList, arrayKey)
                    keyNo++
                }
                val chatHBarModel = DynamicModel(arrayKey, mapList, mapImage).apply {
                    barWidth = toDp(25f) //条形宽度 isAutoBarWidth为false 才可用
                    yCount = 10 //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
                    xCount = 4 //X轴横线刻度数 ，包含 0，比如0到5 设置为 6
                    keyTextOffsetX = toDp(90f) //年份相对右下角X便宜
                    keyTextOffextY = toDp(90f) //年份相对右下角Y便宜
                    xLableStep = 1 //X刻度上面文案显示步长
                    isAutoBarWidth = true  //是否自动计算条形宽度

                    offsetx = toDp(108f) //UI上原点左下角 x偏移
                    offsetxLable = toDp(30f)//原点 y轴上面刻度文字x偏移  相对控件最左边偏移
                    offsety = toDp(3f) //UI上原点左下角 y 偏移
                    offsetyLable = toDp(8f)//原点 y上面刻度文字文字 y偏移 相对控件左下角点,调整Y值文字在竖直中间位置 与横线对齐
                    durationMillis = 3000  //每个年份间隔执行动画

                    isTop = true//X轴是否位于上面
                    offextBarTopY = toDp(18f)   //条形距离顶部偏移 ，这里不是视图，是相对Y轴线最上端
                    offextBarBottomY = toDp(5f) //条形距离底部偏移 ，这里不是视图，是相对Y轴线最下端

                    formatString = "%.2f亿" //数字格式化设置
                    multiplier = 1 / 100000000f //数据显示格式所用的乘数
                    musicUrl = "asset:///vv.mp3" //背景音乐，可配置网络链接

                    titleColor = Color.White //每个条形名称颜色
                    dynamicChartName = "1960~2023各国GPD走势Top10" //动态表名称

                    colotLable = Color.White//X轴上面刻度变化文案颜色
                    maxValueOffsetRight = toDp(150f)
                    bgUrl = "https://q5.itc.cn/q_70/images01/20240723/42bdcb602f35471eadddb09908b683f1.jpeg"//整个动画背景图
                }
                emit(chatHBarModel)
            }.collect {
                _datas.value = it
            }
        }
    }

    private fun doListByKey(
        mList: List<MutableMap<String, String>>, key: String, mapList: MutableMap<String, MutableList<DynamicBarBean>>, arrayKey: Array<DynamicKey>,
    ): MutableList<DynamicBarBean> {
        val list = mutableListOf<DynamicBarBean>()
        val listNext = mutableListOf<DynamicBarBean>()

        val mapNextNo by lazy { mutableMapOf<String, Int>() }
        val nextMap by lazy { mutableMapOf<String, Float>() }

        val nextKeyNo = key.toInt() + 1

        mList.forEachIndexed { index, it ->
            if (it.contains(key)) {
                val value = if (TextUtils.isEmpty(it[key])) 0.00f else it[key]!!.toFloat()
                list.add(DynamicBarBean(value, listColor[index], it["CountryName"]!!))
            } else {
                list.add(DynamicBarBean(0.00f, listColor[index], it["CountryName"]!!))
            }
            if (nextKeyNo <= 2023) {
                if (it.contains(nextKeyNo.toString())) {
                    val value = if (TextUtils.isEmpty(it[nextKeyNo.toString()])) 0.00f else it[nextKeyNo.toString()]!!.toFloat()
                    listNext.add(DynamicBarBean(value, listColor[index], it["CountryName"]!!))
                } else {
                    listNext.add(DynamicBarBean(0.00f, listColor[index], it["CountryName"]!!))
                }
            }
        }
        list.sortBy { it.value }
        listNext.sortBy { it.value }

        listNext.forEachIndexed { index, it ->
            mapNextNo[it.title] = index
            nextMap[it.title] = it.value
        }

        var maxc = 0f
        list.forEachIndexed { index, it ->
            it.currPos = index
            it.nextPos = if (nextKeyNo > 2023) it.currPos else mapNextNo[it.title]!!
            it.diffPos = it.nextPos - it.currPos
            it.diffValue = if (nextKeyNo > 2023) 0f else (nextMap[it.title]!! - it.value)
            it.value.takeIf { v ->
                v > maxc
            }?.let {
                maxc = it
            }
        }

        val maxF = maxc / 100000000000f
        var xMaxValue = Math.floor(maxF.toDouble()).toInt() * 100000000000f
//        android.util.Log.e("DynamicViewModel", "${key} maxF:$maxF    xMaxValue:$xMaxValue      xMaxValueNext:$xMaxValueNext")

        arrayKey[key.toInt() - 1960] = DynamicKey(key, maxc, xMaxValue)
        mapList[key] = list
        return list
    }

    private fun createImageBitmap(): ImageBitmap {
        val imageBitmap = ImageBitmap(100, 100)
        val canvas = Canvas(imageBitmap)
        val paint = Paint()
        paint.color = Color.Transparent
        canvas.drawRect(Rect(50.0f, 50.0f, 100.0f, 100.0f), paint)
        return imageBitmap
    }

    fun toDp(size: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, MyApp.application.resources.displayMetrics)

    private fun getFromAssets(fileName: String): String {
        try {
            val inputReader = InputStreamReader(MyApp.application.assets.open(fileName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result = StringBuilder()
            while (bufReader.readLine().also { line = it } != null) result.append(line)
            return result.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}