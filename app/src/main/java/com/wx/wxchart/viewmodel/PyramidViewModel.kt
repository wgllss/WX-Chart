package com.wx.wxchart.viewmodel

import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wx.chart.data.ChartPyramidBean
import com.wx.chart.data.ChartPyramidModel
import com.wx.chart.data.ChartPyramidMultiBean
import com.wx.chart.data.ChartPyramidMultiModel
import com.wx.chart.data.ChartPyramidRoleBean
import com.wx.chart.data.ChartPyramidRoleModel
import com.wx.chart.data.dynamic.DynamicPyramidModel
import com.wx.chart.data.dynamic.DynamicPyramidMultiModel
import com.wx.chart.data.dynamic.DynamicPyramidRoleModel
import com.wx.chart.data.dynamic.ImageKey
import com.wx.chart.utils.DefaultBitmapUtils.createImageBitmap
import com.wx.wxchart.MyApp
import com.wx.wxchart.data.WXData

class PyramidViewModel : ViewModel() {

    private val _datas2 = MutableLiveData<DynamicPyramidModel>()
    val pyramidModel2: LiveData<DynamicPyramidModel> = _datas2


    private val _datas5 = MutableLiveData<DynamicPyramidRoleModel>()
    val pyramidModel5: LiveData<DynamicPyramidRoleModel> = _datas5

    private val _datas7 = MutableLiveData<DynamicPyramidMultiModel>()
    val pyramidModel7: LiveData<DynamicPyramidMultiModel> = _datas7

    fun setData2() {
        val chartPyramidBean1 = ChartPyramidBean(
            Color(0x30FF0000), mutableListOf(
                "北京", "香港", "上海"
            ), TextStyle(
                fontSize = 19.sp, fontWeight = FontWeight.Normal, color = Color.White
            )
        )
        val chartPyramidBean2 = ChartPyramidBean(
            Color(0x6000FF00), mutableListOf(
                "广州", "深圳", "杭州", "南京", "苏州"
            ), TextStyle(
                fontSize = 23.sp, fontWeight = FontWeight.Normal, color = Color.White
            )
        )
        val chartPyramidBean3 = ChartPyramidBean(
            Color(0x90FFFF00), mutableListOf(
                "成都", "武汉", "重庆", "天津", "宁波", "无锡", "青岛"
            ), TextStyle(
                fontSize = 25.sp, fontWeight = FontWeight.Normal, color = Color.White
            )
        )
        val chartPyramidBean4 = ChartPyramidBean(
            Color(0x600000FF), mutableListOf(
                "长沙", "西安", "沈阳", "郑州", "福州", "佛山", "东莞", "厦门", "济南"
            ), TextStyle(
                fontSize = 28.sp, fontWeight = FontWeight.Normal, color = Color.White
            ), false
        )
        val chartPyramidBean5 = ChartPyramidBean(
            Color(0x90FF00FF), mutableListOf(
                "合肥", "大连", "珠海", "常州", "绍兴", "昆明", "南通", "长春", "温州", "泉州", "嘉兴"
            ), TextStyle(
                fontSize = 28.sp, fontWeight = FontWeight.Normal, color = Color.White
            ), false
        )

        val list = mutableListOf(chartPyramidBean1, chartPyramidBean2, chartPyramidBean3, chartPyramidBean4, chartPyramidBean5)
        val intervalSize = toDp(10f)
        val pyramidModel = DynamicPyramidModel(
            list, intervalSize
        ).apply {
            firstMarginTop = toDp(5f)
            bgUrl = "https://q5.itc.cn/q_70/images01/20240723/42bdcb602f35471eadddb09908b683f1.jpeg"
            animateStyle = 2
            musicUrl = "asset:///vv.mp3" //背景音乐，可配置网络链接
        }
        _datas2.value = pyramidModel
    }


    fun setData5() {
        val chartPyramidBean1 = ChartPyramidRoleBean(
            Color(0x30FF0000), title = "TIER1", mutableListOf(
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/893.png", "乔丹")
            ), TextStyle(
                fontSize = 15.sp, fontWeight = FontWeight.Normal, color = Color.White
            )
        )

        val chartPyramidBean2 = ChartPyramidRoleBean(
            Color(0x6000FF00), title = "TIER2", mutableListOf(
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/78049.png", "比尔拉塞尔"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/977.png", "科比"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/406.png", "奥尼尔"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/77142.png", "魔术师 约翰逊"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/76003.png", "卡里姆阿杜勒-贾巴尔"),
            ), TextStyle(
                fontSize = 15.sp, fontWeight = FontWeight.Normal, color = Color.White
            )
        )
        val chartPyramidBean3 = ChartPyramidRoleBean(
            Color(0x90FF00FF), title = "TIER3", mutableListOf(
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/1717.png", "德克诺维茨基"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/1495.png", "邓肯"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/2544.png", "勒布朗詹姆斯"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/201142.png", "凯文杜兰特"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/76681.png", "朱利叶斯欧文"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/1449.png", "拉里伯德"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/76375.png", "维尔特张伯伦"),
            ), TextStyle(
                fontSize = 15.sp, fontWeight = FontWeight.Normal, color = Color.White
            )
        )
        val chartPyramidBean4 = ChartPyramidRoleBean(
            Color(0x600000FF), title = "TIER4", mutableListOf(
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/252.png", "卡尔马龙"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/708.png", "加内特"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/78318.png", "伊赛亚托马斯"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/2548.png", "德怀恩韦德"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/764.png", "大卫罗宾逊"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/600015.png", "奥斯卡罗伯特森"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/201939.png", "斯蒂芬库里"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/787.png", "查尔斯巴克利"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/78497.png", "杰里韦斯特"),
            ), TextStyle(
                fontSize = 15.sp, fontWeight = FontWeight.Normal, color = Color.White
            ), true
        )
        val chartPyramidBean5 = ChartPyramidRoleBean(
            Color(0x90FFFF00), title = "TIER5", mutableListOf(
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/304.png", "约翰斯托克顿"),
                ImageKey("https://so1.360tres.com/t0135f038eaccda7685.jpg", "蒙克利夫"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/76970.png", "约翰哈福利切克"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/76970.png", "摩西马龙"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/76979.png", "埃尔文海斯"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/937.png", "斯科蒂皮蓬"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/947.png", "阿伦艾弗森"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/1713.png", "文斯卡特"),
                ImageKey("https://res.nba.cn/media/img/players/head/260x190/959.png", "史蒂夫纳什"),
            ), TextStyle(
                fontSize = 15.sp, fontWeight = FontWeight.Normal, color = Color.White
            ), true
        )
        val list = mutableListOf(chartPyramidBean1, chartPyramidBean2, chartPyramidBean3, chartPyramidBean4, chartPyramidBean5)
        val intervalSize = toDp(10f)
        val mapImage = mutableMapOf<String, ImageBitmap>()
        list.forEach {
            it.datas.forEach {
                mapImage[it.imgUrl] = createImageBitmap()
            }
        }

        val pyramidModel = DynamicPyramidRoleModel(
            list, intervalSize, mapImage
        ).apply {
            firstMarginTop = toDp(5f)
            bgUrl = "https://img0.baidu.com/it/u=229341551,2326494805&fm=253&fmt=auto?w=889&h=500"
            animateStyle = 2
            musicUrl = "asset:///vv.mp3" //背景音乐，可配置网络链接
        }
        _datas5.value = pyramidModel
    }


    fun setData7() {
//        val titleFontSize = 15.sp
//        val fontSize1 = 12.sp
//        val fontSize2 = 8.sp
//        val fontSize3 = 9.sp
//        val fontSize4 = 10.sp

        val titleFontSize = 20.sp
        val fontSize1 = 21.sp
        val fontSize2 = 12.sp
        val fontSize3 = 13.sp
        val fontSize4 = 13.sp

        val chartPyramidBean1 = ChartPyramidMultiBean(
            Color(0x30FF0000), title = "超一线", mutableListOf(
                ImageKey(gq("美国"), "纽约"),
                ImageKey(gq("英国"), "伦敦"),
            ), 2, TextStyle(
                fontSize = titleFontSize, fontWeight = FontWeight.Bold, color = Color.Red
            ), textStyle = TextStyle(
                fontSize = fontSize1, fontWeight = FontWeight.Bold, color = Color.White
            )
        )
        val chartPyramidBean2 = ChartPyramidMultiBean(
            Color(0x30ABC000), title = "世界强一线城市", mutableListOf(
                ImageKey(gq("中国"), "香港"),
                ImageKey(gq("中国"), "北京"),
                ImageKey(gq("中国"), "上海"),
                ImageKey(gq("新加坡"), "新加坡"),
                ImageKey(gq("法国"), "巴黎"),
                ImageKey(gq("阿联酋"), "迪拜"),
                ImageKey(gq("日本"), "东京"),
                ImageKey(gq("澳大利亚"), "悉尼"),
            ), 8, TextStyle(
                fontSize = titleFontSize, fontWeight = FontWeight.Bold, color = Color.Red
            ), textStyle = TextStyle(
                fontSize = fontSize2, fontWeight = FontWeight.Bold, color = Color.White
            )
        )

        val chartPyramidBean3 = ChartPyramidMultiBean(
            Color(0x600000FF), title = "世界一线城市-16座", mutableListOf(
                ImageKey(gq("韩国"), "首尔"),
                ImageKey(gq("意大利"), "米兰"),
                ImageKey(gq("中国"), "广州"),
                ImageKey(gq("加拿大"), "多伦多"),
                ImageKey(gq("荷兰"), "阿姆斯特丹"),
                ImageKey(gq("美国"), "芝加哥"),
                ImageKey(gq("印度尼西亚"), "雅加达"),
                ImageKey(gq("巴西"), "圣保罗"),
                ImageKey(gq("马来西亚"), "吉隆坡"),
                ImageKey(gq("美国"), "洛杉矶"),
                ImageKey(gq("泰国"), "曼谷"),
                ImageKey(gq("西班牙"), "马德里"),
                ImageKey(gq("土耳其"), "伊斯坦布尔"),
                ImageKey(gq("德国"), "法兰克福"),
                ImageKey(gq("墨西哥"), "墨西哥城"),
                ImageKey(gq("印度"), "孟买"),
            ), 8, TextStyle(
                fontSize = titleFontSize, fontWeight = FontWeight.Bold, color = Color.Red
            ), textStyle = TextStyle(
                fontSize = fontSize3, fontWeight = FontWeight.Bold, color = Color.White
            )
        )

        val chartPyramidBean4 = ChartPyramidMultiBean(
            Color(0x90FFFF00), title = "世界弱一线城市-22座", mutableListOf(
                ImageKey(gq("卢森堡"), "卢森堡"),
                ImageKey(gq("中国"), "台北"),
                ImageKey(gq("中国"), "深圳"),
                ImageKey(gq("比利时"), "布鲁塞尔"),
                ImageKey(gq("瑞士"), "苏黎世"),
                ImageKey(gq("阿根廷"), "布宣诺斯艾利斯"),
                ImageKey(gq("澳大利亚"), "墨尔本"),
                ImageKey(gq("美国"), "旧金山"),
                ImageKey(gq("沙特阿拉伯"), "利雅德"),
                ImageKey(gq("智利"), "圣地亚哥"),
                ImageKey(gq("印度"), "新德里"),
                ImageKey(gq("德国"), "杜塞尔多夫"),
                ImageKey(gq("瑞典"), "斯德哥尔摩"),
                ImageKey(gq("美国"), "华盛顿"),
                ImageKey(gq("德国"), "柏林"),
                ImageKey(gq("奥地利"), "维也纳"),
                ImageKey(gq("葡萄牙"), "里斯本"),
                ImageKey(gq("德国"), "慕尼黑"),
                ImageKey(gq("爱尔兰"), "都柏林"),
                ImageKey(gq("美国"), "休斯顿"),
                ImageKey(gq("南非"), "约翰内斯堡"),
                ImageKey(gq("美国"), "波士顿"),
            ), 11, TextStyle(
                fontSize = titleFontSize, fontWeight = FontWeight.Bold, color = Color.Red
            ), textStyle = TextStyle(
                fontSize = fontSize2, fontWeight = FontWeight.Bold, color = Color.White
            )
        )

        val chartPyramidBean5 = ChartPyramidMultiBean(
            Color(0x90FF00FF), title = "世界强二线城市-20座", mutableListOf(
                ImageKey(gq("哥伦比亚"), "波哥大"),
                ImageKey(gq("越南"), "胡志明"),
                ImageKey(gq("意大利"), "罗马"),
                ImageKey(gq("印度"), "班加罗尔"),
                ImageKey(gq("匈牙利"), "布达佩斯"),
                ImageKey(gq("希腊"), "雅典"),
                ImageKey(gq("德国"), "汉堡"),
                ImageKey(gq("卡塔尔"), "多哈"),
                ImageKey(gq("中国"), "成都"),
                ImageKey(gq("美国"), "吗哈密"),
                ImageKey(gq("中国"), "天津"),
                ImageKey(gq("美国"), "达拉斯"),
                ImageKey(gq("美国"), "亚特兰大"),
                ImageKey(gq("新西兰"), "奥克兰"),
                ImageKey(gq("西班牙"), "巴塞罗那"),
                ImageKey(gq("中国"), "杭州"),
                ImageKey(gq("罗马尼亚"), "布加勒斯特"),
                ImageKey(gq("秘鲁"), "利马"),
                ImageKey(gq("加拿大"), "蒙特利尔"),
                ImageKey(gq("捷克"), "布拉格"),
            ), 10, TextStyle(
                fontSize = titleFontSize, fontWeight = FontWeight.Bold, color = Color.White
            ), isRectangle = true, textStyle = TextStyle(
                fontSize = fontSize4, fontWeight = FontWeight.Bold, color = Color.White
            )
        )
        val chartPyramidBean6 = ChartPyramidMultiBean(
            Color(0x6000FF00), title = "世界二线城市-23座", mutableListOf(
                ImageKey(gq("中国"), "重庆"),
                ImageKey(gq("以色列"), "特拉维夫"),
                ImageKey(gq("澳大利亚"), "布里斯班"),
                ImageKey(gq("埃及"), "开罗"),
                ImageKey(gq("越南"), "河内"),
                ImageKey(gq("中国"), "南京"),
                ImageKey(gq("挪威"), "奥斯陆"),
                ImageKey(gq("澳大利亚"), "珀斯"),
                ImageKey(gq("阿联酋"), "阿布扎比"),
                ImageKey(gq("丹麦"), "哥本哈根"),
                ImageKey(gq("巴林"), "麦纳麦"),
                ImageKey(gq("中国"), "武汉"),
                ImageKey(gq("菲律宾"), "马尼拉"),
                ImageKey(gq("中国"), "厦门"),
                ImageKey(gq("肯尼亚"), "内罗毕"),
                ImageKey(gq("乌克兰"), "基辅"),
                ImageKey(gq("瑞士"), "日内瓦"),
                ImageKey(gq("中国"), "济南"),
                ImageKey(gq("加拿大"), "卡尔加里"),
                ImageKey(gq("中国"), "郑州"),
                ImageKey(gq("中国"), "沈阳"),
                ImageKey(gq("中国"), "大连"),
                ImageKey(gq("中国"), "苏州"),
            ), 12, TextStyle(
                fontSize = titleFontSize, fontWeight = FontWeight.Bold, color = Color.White
            ), isRectangle = true, textStyle = TextStyle(
                fontSize = fontSize4, fontWeight = FontWeight.Bold, color = Color.White
            )
        )


        val list = mutableListOf(chartPyramidBean1, chartPyramidBean2, chartPyramidBean3, chartPyramidBean4, chartPyramidBean5, chartPyramidBean6)
        val intervalSize = toDp(10f)
        val mapImage = mutableMapOf<String, ImageBitmap>()
        list.forEach {
            it.datas.forEach {
                mapImage[it.imgUrl] = createImageBitmap()
            }
        }

        val pyramidModel = DynamicPyramidMultiModel(
            list, intervalSize, mapImage
        ).apply {
            firstMarginTop = toDp(25f)
//            bgUrl = "https://q5.itc.cn/q_70/images01/20240723/42bdcb602f35471eadddb09908b683f1.jpeg"
            bgUrl = "https://img2.baidu.com/it/u=933883660,346286397&fm=253&fmt=auto?w=800&h=1422"
            animateStyle = 2
            musicUrl = "asset:///vv.mp3" //背景音乐，可配置网络链接
        }
        _datas7.value = pyramidModel
    }

    private fun gq(key: String): String {
        return WXData.getGuoQiImg(key, MyApp.application.packageName)
    }

    private fun toDp(size: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, MyApp.application.resources.displayMetrics)
}