package com.wx.wxchart.viewmodel

import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wx.chart.data.dynamic.DynamicImage
import com.wx.chart.data.dynamic.DynamicKey
import com.wx.chart.data.dynamic.DynamicModel
import com.wx.chart.data.dynamic.DynamicPKBarBean
import com.wx.chart.data.dynamic.DynamicPKModel
import com.wx.chart.data.dynamic.DynamicPKRoleModel
import com.wx.chart.data.dynamic.DynamicTopBarBean
import com.wx.chart.data.dynamic.top.DynamicTopModel
import com.wx.chart.utils.DefaultBitmapUtils
import com.wx.chart.utils.DefaultBitmapUtils.createImageBitmap
import com.wx.chart.utils.DynamicBarUtils
import com.wx.chart.utils.DynamicTopUtils
import com.wx.wxchart.MyApp
import com.wx.wxchart.data.TopDataSource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class DynamicViewModel : ViewModel() {

    private val _datas = MutableLiveData<DynamicModel>()
    val dynamicModel: LiveData<DynamicModel> = _datas

    private val _datas2 = MutableLiveData<DynamicTopModel>()
    val dynamicTopModel: LiveData<DynamicTopModel> = _datas2

    private val _datas3 = MutableLiveData<DynamicPKModel>()
    val dynamicPKModel: LiveData<DynamicPKModel> = _datas3

    private val _datas5 = MutableLiveData<DynamicPKRoleModel>()
    val dynamicPKModel5: LiveData<DynamicPKRoleModel> = _datas5

    private val _datas6 = MutableLiveData<DynamicPKModel>()
    val dynamicPKModel6: LiveData<DynamicPKModel> = _datas6

    private val listColor = listOf(Color.Cyan, Color.Green, Color.Blue, Color.Magenta, Color.Yellow, Color.Red, Color.Gray, Color(0xF0EFD5A0), Color(0xFFF0FD00), Color(0xFACE5DA0))

    fun setData() {
        viewModelScope.launch {
            flow {
                val gson = Gson()
                val json = getFromAssets("txt/txt.txt")
                val dto = gson.fromJson<List<MutableMap<String, String>>>(json, object : TypeToken<List<MutableMap<String, String>>>() {}.type)

                val mapImage = mutableMapOf<String, DynamicImage>()
                val defaultBitmap = DefaultBitmapUtils.createImageBitmap()
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

                val xMaxValueF = 100000000f
                val keySize = dto[0].size - 4
                val titleNameKey = "CountryName"

                val chatHBarModel = DynamicBarUtils.convertDynamicBarModel(
                    dto, keySize, mapImage, titleNameKey, xMaxValueF, listColor
                ) { listKey, key ->
                    if (!key.contains("CountryName") && !key.contains("CountryCode") && !key.contains("IndicatorName") && !key.contains("IndicatorCode")) {
                        listKey.add(key)
                    }
                }.apply {
                    barWidth = toDp(25f) //条形宽度 isAutoBarWidth为false 才可用
                    yCount = 10 //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
                    xCount = 4 //X轴横线刻度数 ，包含 0，比如0到5 设置为 6
                    keyTextOffsetX = toDp(90f) //年份相对右下角X便宜
                    keyTextOffextY = toDp(90f) //年份相对右下角Y便宜
                    xLableStep = 1 //X刻度上面文案显示步长
                    isAutoBarWidth = true  //是否自动计算条形宽度
                    isShowRightTitle = false
                    titleFontSize = 35

                    offsetx = toDp(158f) //UI上原点左下角 x偏移
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
                    titleFontSize = 16

                    colorLable = Color.White//X轴上面刻度变化文案颜色
                    maxValueOffsetRight = toDp(180f)
                    bgUrl = "https://q5.itc.cn/q_70/images01/20240723/42bdcb602f35471eadddb09908b683f1.jpeg"//整个动画背景图
                }
                emit(chatHBarModel)
            }.collect {
                _datas.value = it
            }
        }
    }

    fun setData2() {
        viewModelScope.launch {
            flow {

                val list = getSourceList()
                val size = list.size
                val defaultBitmap = createImageBitmap()
                val showNum = 12

                val dynamicTopModel = DynamicTopUtils.convertTopModel(list, showNum) { index: Int, sortList: MutableList<DynamicTopBarBean>, mapImage: MutableMap<Int, DynamicImage>, arrayKey, it: TopDataSource ->
                    sortList.add(DynamicTopBarBean(it.value, listColor[index % 10], it.title, index + 1, it.img))
                    mapImage[index + 1] = DynamicImage(it.img, defaultBitmap)
                    val maxF = it.value / 10
                    var xMaxValue = Math.floor(maxF.toDouble()).toInt() * 10F
                    arrayKey[size - 1 - index] = DynamicKey((size - 1 - index).toString(), it.value, xMaxValue)
                }.apply {
                    topSize = size
                    barWidth = toDp(25f) //条形宽度 isAutoBarWidth为false 才可用
                    yCount = showNum //y轴横线刻度数 ，包含 0，比如0到5 设置为 6
                    xCount = 4 //X轴横线刻度数 ，包含 0，比如0到5 设置为 6
                    keyTextOffsetX = toDp(110f) //年份相对右下角X便宜
                    keyTextOffextY = toDp(90f) //年份相对右下角Y便宜
                    xLableStep = 1 //X刻度上面文案显示步长
                    isAutoBarWidth = true  //是否自动计算条形宽度

                    offsetx = toDp(90f) //UI上原点左下角 x偏移
                    offsetxLable = toDp(30f)//原点 y轴上面刻度文字x偏移  相对控件最左边偏移
                    offsety = toDp(3f) //UI上原点左下角 y 偏移
                    offsetyLable = toDp(8f)//原点 y上面刻度文字文字 y偏移 相对控件左下角点,调整Y值文字在竖直中间位置 与横线对齐
                    durationMillis = 3000  //每个年份间隔执行动画

                    isTop = true//X轴是否位于上面
                    offextBarTopY = toDp(18f)   //条形距离顶部偏移 ，这里不是视图，是相对Y轴线最上端
                    offextBarBottomY = toDp(5f) //条形距离底部偏移 ，这里不是视图，是相对Y轴线最下端

                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                    musicUrl = "asset:///vv.mp3" //背景音乐，可配置网络链接

                    titleColor = Color.White //每个条形名称颜色
                    dynamicChartName = "优质作者榜(客户端)：2024年第11期月榜" //动态表名称
                    isShowCurrentImg = true
                    colorLable = Color.White//X轴上面刻度变化文案颜色
                    maxValueOffsetRight = toDp(180f)
//                    bgUrl = "https://copyright.bdstatic.com/vcg/creative/820617f247e3a7f89686800e43c62ab2.jpg"//整个动画背景图
                    bgUrl = "https://lmg.jj20.com/up/allimg/1114/0P421133347/210P4133347-1-1200.jpg"//整个动画背景图
                }
                emit(dynamicTopModel)
            }.collect {
                _datas2.value = it
            }
        }
    }

    private fun getSourceList(): MutableList<TopDataSource> {
        val list = mutableListOf<TopDataSource>()
        list.add(TopDataSource("恋猫de小郭", "https://p3-passport.byteacctimg.com/img/user-avatar/4f40c8c1bc9e95d86779e105c922ca2f~300x300.image", 1158f))
        list.add(TopDataSource("Wgllss", "https://p6-passport.byteacctimg.com/img/user-avatar/cdf8ff3d0fc94ae7f1c5cd90b5a0ae35~300x300.image", 840f))
        list.add(TopDataSource("张风捷特烈", "https://p9-passport.byteacctimg.com/img/user-avatar/5b2b7b85d1c818fa71d9e2e8ba944a44~300x300.image", 713f))
        list.add(TopDataSource("小墙程序员", "https://p9-passport.byteacctimg.com/img/user-avatar/c20e903aa0eecae09a2bd5593ef58db7~300x300.image", 582f))
        list.add(TopDataSource("法的空间", "https://p3-passport.byteacctimg.com/img/user-avatar/41fe666b46afd29d2da26222a6241e94~300x300.image", 535f))
        list.add(TopDataSource("稀有猿诉", "https://p3-passport.byteacctimg.com/img/user-avatar/2cf639585a7793ada0c7957d5b3a5735~300x300.image", 342f))
        list.add(TopDataSource("陆业聪", "https://p6-passport.byteacctimg.com/img/user-avatar/74a60ecfd93fad88c8fbe59e3b2cc2d4~300x300.image", 294f))
        list.add(TopDataSource("半山居士", "https://p26-passport.byteacctimg.com/img/user-avatar/3060d9b49a1c858c4f9f3b43d2fa7fd4~300x300.image", 266f))
        list.add(TopDataSource("奇风FantasyWind", "https://p6-passport.byteacctimg.com/img/user-avatar/bbb88e1ebef2a8a4ae1d3b41933bd684~300x300.image", 238f))
        list.add(TopDataSource("一杯凉白开", "https://p6-passport.byteacctimg.com/img/user-avatar/b02d71c859387330709540f93c4cfe3c~300x300.image", 231f))
        list.add(TopDataSource("青衫", "https://p26-passport.byteacctimg.com/img/user-avatar/fdcc3658bfd48a576d113d145aa25305~300x300.image", 221f))
        list.add(TopDataSource("_小马快跑_", "https://p6-passport.byteacctimg.com/img/user-avatar/1678f071bd16e3016f45068b9ac865dc~300x300.image", 211f))
        list.add(TopDataSource("程序员DHL", "https://p3-passport.byteacctimg.com/img/user-avatar/5166799b3da871fdc15f8c0275741284~300x300.image", 186f))
        list.add(TopDataSource("朱涛的自习室", "https://p3-passport.byteacctimg.com/img/user-avatar/833b93a0841a26548e37f128d4b01aaf~300x300.image", 181f))
        list.add(TopDataSource("芦半山", "https://p26-passport.byteacctimg.com/img/user-avatar/ae2d7f8f77b2bd99d9c32a587b1bec97~300x300.image", 165f))
        list.add(TopDataSource("IAM四十二", "https://p6-passport.byteacctimg.com/img/user-avatar/98b3934295f138b26c259cb2a9be0fec~300x300.image", 162f))
        list.add(TopDataSource("iOS阿玮", "https://p9-passport.byteacctimg.com/img/user-avatar/f9bc81aa8a0c2a42f27fb87689873b30~300x300.image", 148f))
        list.add(TopDataSource("vivo高启强", "https://p6-passport.byteacctimg.com/img/user-avatar/7fba93d8babd024219d3bc2abfb6dff8~300x300.image", 133f))
        list.add(TopDataSource("IT小码哥", "https://p3-passport.byteacctimg.com/img/user-avatar/308fcfe3062ce610355c3a5cd4387b61~300x300.image", 126f))
        list.add(TopDataSource("天枢破军", "https://p6-passport.byteacctimg.com/img/user-avatar/3ec2b49f3956a7402720672798e10cf7~300x300.image", 124f))
//        list.sortedByDescending { it.value }//从大到小 降序
        list.sortByDescending { it.value }//从大到小 降序
        return list
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

    fun setData3() {
        val dynamicPKModel = DynamicPKModel(
            list = mutableListOf(
                DynamicPKBarBean(
                    "得分", 107f, 128f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "篮板", 44f, 49f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "助攻", 28f, 35f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "抢断", 4f, 8f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "盖帽", 5f, 6f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "失误", 14f, 11f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "投篮命中率", 0.451f, 0.526f
                ).apply {
                    formatString = "%.1f" //数字格式化设置
                    multiplier = 100f //数据显示格式所用的乘数
                    enfBuff = "%"
                }, DynamicPKBarBean(
                    "三分命中率", 0.375f, 0.545f
                ).apply {
                    formatString = "%.1f" //数字格式化设置
                    multiplier = 100f //数据显示格式所用的乘数
                    enfBuff = "%"
                }, DynamicPKBarBean(
                    "罚球命中率", 0.625f, 1f
                ).apply {
                    formatString = "%.1f" //数字格式化设置
                    multiplier = 100f //数据显示格式所用的乘数
                    enfBuff = "%"
                }, DynamicPKBarBean(
                    "时间", 48f, 48f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }), centerWidth = toDp(110f)
        ).apply {
            pkLeftName = "马刺"
            pkLeftImgUrl = "https://search-operate.cdn.bcebos.com/5305d1a7b721b5bef418041eff53ba82.png"
            pkRightName = "热火"
            pkRightImgUrl = "https://search-operate.cdn.bcebos.com/ff7ccef6a6b79c6417ee8367946b0aec.png"
            win1Color = Color.Magenta
            win2Color = Color.Red
            loseColor = Color.Gray
            otherBgColor = Color.LightGray
            eqColor = Color.Green
            offsetLeft = toDp(10f)
            offsetRight = toDp(10f)
            marginDiv = toDp(10f)
//            barSize = toDp(20f)
            musicUrl = "asset:///vv.mp3" //背景音乐，可配置网络链接
        }
        _datas3.value = dynamicPKModel
    }

    fun setData5() {
        val mapLeftImage = mutableMapOf(
            0 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/1630170.png", createImageBitmap()),
            1 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/1641705.png", createImageBitmap()),
            2 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/101108.png", createImageBitmap()),
            3 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/1629646.png", createImageBitmap()),
            4 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/202710.png", createImageBitmap()),
        )
        val mapRightImage = mutableMapOf(
            0 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/1642276.png", createImageBitmap()),
            1 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/1628389.png", createImageBitmap()),
            2 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/202710.png", createImageBitmap()),
            3 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/1631170.png", createImageBitmap()),
            4 to DynamicImage("https://res.nba.cn/media/img/players/head/260x190/1630170.png", createImageBitmap()),
        )

        val dynamicPKModel = DynamicPKRoleModel(
            list = mutableListOf(
                DynamicPKBarBean(
                    "得分", 23f, 25f, "德文 瓦塞尔", "https://res.nba.cn/media/img/players/head/260x190/1630170.png", "凯尔 韦尔", "https://res.nba.cn/media/img/players/head/260x190/1642276.png"
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "篮板", 10f, 11f, "维克托 文班亚马", "https://res.nba.cn/media/img/players/head/260x190/1641705.png", "巴姆 阿德巴约", "https://res.nba.cn/media/img/players/head/260x190/1628389.png"
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "助攻", 9f, 7f, "克里斯 保罗", "https://res.nba.cn/media/img/players/head/260x190/101108.png", "吉米 巴特勒", "https://res.nba.cn/media/img/players/head/260x190/202710.png"
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "抢断", 2f, 3f, "查尔斯 巴锡", "https://res.nba.cn/media/img/players/head/260x190/1629646.png", "海梅 雅克", "https://res.nba.cn/media/img/players/head/260x190/1631170.png"
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "盖帽", 2f, 2f, "德文 瓦塞尔", "https://res.nba.cn/media/img/players/head/260x190/1630170.png", "吉米 巴特勒", "https://res.nba.cn/media/img/players/head/260x190/202710.png"
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }), mapLeftImage = mapLeftImage, mapRightImage = mapRightImage
        ).apply {
            pkLeftName = "马刺"
            pkLeftImgUrl = "https://search-operate.cdn.bcebos.com/5305d1a7b721b5bef418041eff53ba82.png"
            pkRightName = "热火"
            pkRightImgUrl = "https://search-operate.cdn.bcebos.com/ff7ccef6a6b79c6417ee8367946b0aec.png"
            win1Color = Color.Magenta
            win2Color = Color.Red
            loseColor = Color.Gray
            otherBgColor = Color.LightGray
            eqColor = Color.Green
            offsetLeft = toDp(10f)
            offsetRight = toDp(10f)
            marginDiv = toDp(10f)
//            barSize = toDp(66f)
        }

        _datas5.value = dynamicPKModel
    }

    fun setData6() {
        val dynamicPKModel = DynamicPKModel(
            list = mutableListOf(
                DynamicPKBarBean(
                    "经济总量(单位：万亿美元)", 17.8f, 27.37f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
//                enfBuff = "%"
                }, DynamicPKBarBean(
                    "人均GDP(单位：万美元)", 1.27f, 8.15f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "人口(单位：亿人)", 14.11f, 3.35f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "面积(单位：万平方公里)", 960f, 937f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "黄金醋备(单位：吨)", 2245.00f, 8133.60f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "股市市值(单位：万亿美元)", 11.86f, 63.60f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "世界五百强企业数量(单位：家)", 133f, 139f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "本世纪奥运金牌数(单位：枚)", 222f, 242f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "消灭倭寇军队数(单位：万人)", 70.00f, 200f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "PPP购买力平价(单位：万亿美元)", 34.64f, 27.36f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "发明专利数(单位：件)", 69610f, 55678f
                ).apply {
                    formatString = "%.0f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "工业增加值(单位：万亿美元)", 5.66f, 2.80f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "手机产量(单位：亿台)", 15.70f, 2.34f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "电脑产量(单位：亿台)", 3.30f, 0.70f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "汽车产量(单位：万辆)", 3016.00f, 1061.00f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "集装箱吞吐量(单位：亿标准箱)", 3.10f, 0.62f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "造船产量(单位：万载重吨)", 4232.00f, 10.00f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "高铁里程(单位：公里)", 45000.00f, 56.00f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "导航卫星数(单位：颗)", 55.00f, 24.00f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "钢铁产量(单位：亿吨)", 10.19f, 0.80f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }, DynamicPKBarBean(
                    "发电量(单位：万亿度)", 9.50f, 4.20f
                ).apply {
                    formatString = "%.2f" //数字格式化设置
                    multiplier = 1f //数据显示格式所用的乘数
                }
//            , DynamicPKBarBean(
//            "粮食产量(单位：亿吨)", 7.00f, 5.70f
//        ).apply {
//            formatString = "%.2f" //数字格式化设置
//            multiplier = 1f //数据显示格式所用的乘数
//        }, DynamicPKBarBean(
//            "肉类产量(单位：万吨)", 9641.00f, 4600.00f
//        ).apply {
//            formatString = "%.2f" //数字格式化设置
//            multiplier = 1f //数据显示格式所用的乘数
//        }
//                , DynamicPKBarBean(
//                "本国GDP能建造的大驱数量(单位：艘)", 20871f, 10523f
//            ).apply {
//                formatString = "%.0f" //数字格式化设置
//                multiplier = 1f //数据显示格式所用的乘数
//            }, DynamicPKBarBean(
//                "本国GDP能建造的航母数量(单位：艘)", 2500f, 1824f
//            ).apply {
//                formatString = "%.0f" //数字格式化设置
//                multiplier = 1f //数据显示格式所用的乘数
//            }

            ), centerWidth = 0f).apply {
            pkLeftName = "中国"
            pkLeftImgUrl = "https://bkimg.cdn.bcebos.com/pic/d0c8a786c9177f3e67097eaf9c852cc79f3df8dcf874?x-bce-process=image/format,f_auto/resize,m_lfit,limit_1,h_1080"
            pkRightName = "美国"
            pkRightImgUrl = "https://gss0.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/8435e5dde71190ef3ab597f0c01b9d16fdfa6056.jpg"
            win1Color = Color.Red
            win2Color = Color.Magenta
            loseColor = Color.Gray
            eqColor = Color.Gray
            offsetLeft = toDp(50f)
            offsetRight = toDp(50f)
            marginDiv = toDp(3f)
//            barSize = toDp(20f)
            musicUrl = "asset:///vv.mp3" //背景音乐，可配置网络链接
            bgUrl = "https://img.zcool.cn/community/0122e76167eb8f11013e8943c69a3a.jpg"
//            bgUrl = "android.resource://" + MyApp.application.packageName + "/drawable/q3333"
        }
        _datas6.value = dynamicPKModel
    }
}