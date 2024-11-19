
![img_v3_02gp_14677f22-beba-45ff-8d6f-bf564839858g.jpg](https://p0-xtjj-private.juejin.cn/tos-cn-i-73owjymdk6/b718e4e5d6504bb9b423de8dbe1f7223~tplv-73owjymdk6-jj-mark-v1:0:0:0:0:5o6Y6YeR5oqA5pyv56S-5Yy6IEAgV2dsbHNz:q75.awebp?policy=eyJ2bSI6MywidWlkIjoiMzU2NjYxODM1MDgyNTczIn0%3D&rk3s=e9ecf3d6&x-orig-authkey=f32326d3454f2ac7e96d3d06cdbb035152127018&x-orig-expires=1732076757&x-orig-sign=MN%2FiDMk4B4fofaatCvv3o5XvUzE%3D)


## 一、使用WXChart
##### 1、`repositories`中添加如下`maven`
```
    repositories {
        maven { url 'https://repo1.maven.org/maven2/' }
        maven { url 'https://s01.oss.sonatype.org/content/repositories/releases/' }
    }
}
```
#### 2、 `dependencies`中添加依赖
```
implementation("io.github.wgllss:Wgllss-WXChart:1.0.03")
```
#### 3、使用地方 主要调用：` realDrawChart(this@Canvas, width, height, it, textMeasurer, touchIndex, isTouchLast)` 如下：
```

@Composable
fun LineChart(innerPadding: PaddingValues, viewModel: SampleViewModel = SampleViewModel().apply { setData() }) {
    var height by remember { mutableStateOf(0f) }//绘制图表高度
    var width by remember { mutableStateOf(0f) } //绘制图表高度
    var touchIndex by remember { mutableStateOf(-1) }//点击touch 算出水平数据索引
    var isTouchLast by remember { mutableStateOf(false) } // 控制点击绘制浮层，图标最右边时候，可显示在左边
    val textMeasurer = rememberTextMeasurer()

    val chatModel by viewModel.chatModel.observeAsState()


    chatModel?.let {
        Canvas(modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .height(300.dp)
            .onSizeChanged {
                width = it.width.toFloat()
                height = it.height.toFloat()
            }
            //监听手势缩放
            .graphicsLayer()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    touchIndex = getTouchIndex(chatModel, width, it.x, it.y)
                    isTouchLast = if (chatModel != null) {
                        chatModel!!.xCount - 3 <= touchIndex
                    } else false
                })
            }) {
            realDrawChart(this@Canvas, width, height, it, textMeasurer, touchIndex, isTouchLast)
        }
    }
}
```
#### 4、数据提供方

```
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
        yCount = 5, //y轴横线刻度数 ，包含 0，比如0到5 含X轴6条水平
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
```

#### 5、数据说明（`ChartModel的参数`）
参数              | 类型                        | 说明                                            |
| --------------- | ------------------------- | --------------------------------------------- |
| datas           | MutableList\<ChatLineBean> | 多条曲线集                                         |
| listX           | MutableList\<String>       | x轴上刻度文字                                       |
| yCount          | Int                       | y轴横线刻度数 ，包含 0，比如0到5, 含X轴6条水平线                    |
| xCount          | Int                       | //x轴纵向上点数 包含 0 一般为X数据集size 必须大于1              |
| offsetx         | Float                     | UI上原点左下角 x偏移                                  |
| offsetxLable    | Float                     | 原点 y轴上面刻度文字x偏移 相对控件最左边偏移                      |
| offsety         | Float                     | UI上原点左下角 y 偏移                                 |
| offsetyLable    | Float                     | 原点 y上面刻度文字文字 y偏移 相对控件左下角点,调整Y值文字在竖直中间位置 与横线对齐 |
| xLableStep      | Int                       | x轴上刻度对应文字，太多了显示不下，可以设置显示步长。如隔4个显示一个           |
| isShowYLine     | Boolean                   | 否显示Y轴线                                        |
| clickLayerColor | @StableColor              | 点击后展示浮层背景颜色

#### 6、数据对象`ChatLineBean`的参数说明
参数        | 类型                 | 说明                |
| --------- | ------------------ | ----------------- |
| listY     | MutableList\<Float> | 曲线的y值数据集          |
| color     | @StableColor       | 曲线颜色              |
| lineTitle | String             | 曲线名称 比如 收藏曲线 点赞曲线
    
## 二、绘制原理
1. 主要使用的 **`drawLine`,`drawText`，`drawPath`,`drawOval`,`drawRect`** 等绘制方法
2. 绘制过程中的起始点x,y坐标，是以控件左上角为原来，x轴向右、y轴向下为正方向的
但是图表上UI上的原点是左下角原点。
3. 主要的难点在于计算绘制的起始位置，确定UI图表上面的原点位置, 坐标位置计算：**`全是初中坐标知识`**
> 3.1 我们需要计算图表控件的宽高`（width , height）`，UI图标原点距离左下角的偏移位置`(offsetx , offsety)`即为原点,图表上面距离顶部设定也为`offsety`，右边距离控件右边偏移为`offsetx/2`  
> 3.2 原点位置可以得到坐标为`(offsetx , height-offsety)`  
> 3.3 X轴最右边坐标为`(width- offsetx/2 , height-offsety)`  
> 3.4 Y轴最上边坐标为`(offsetx , offsety)`  
> 3.5 X轴上面假设定0~9的刻度` xCount=10` 个数据，相邻2个数据之间间隔x值宽度为 `val xDiv = (width - 1.5f X offsetx) / (xCount - 1)`  
> 3.6 X轴上第1,2,3,4...刻度值的X坐标可以得到：`offsetx + i X xDiv`(其中：`i为x轴数据索引位置,从左到右`)  
> 3.7 Y轴上面`yCount` 设置为`5` , 含X轴`6条水平线`,相邻两条高度间隔坐标y值：`val heightDiv = (height - 2 X offsety) / yCount`  
> 3.8 Y轴上第1,2,3,4...刻度值的Y坐标可以得到：`height - offsety - i X heightDiv`(其中：`i为y轴刻度线条索引位置，从上到下`)  
> 3.9 找出所有数据中的最大值`maxY`，该值对应UI上面`Y轴最上面的y坐标`  
> 3.10 `val yValue = it.maxY / (it.yCount)` 即是Y轴上刻度间隔对应的实际值  
> 3.11 从下到上每一条横线刻度值：`i * yValue`(其中：`i为y轴刻度数位置,从下到上`)    
> 3.12 Y轴上面刻度文案坐标便可以计算出来：`(offsetxLable, height - it.offsety - i X heightDiv - offsetyLable)` (其中：`i为y轴刻度数位置,从下到上`)    
> 3.13 数据最大值和图标Y轴坐标y值对应的比例：`val yAbs = (height - 2 X offsety) / maxY`  
> 3.14 每一条`实际数据`对应图表上面的`Y坐标`便可以计算出来：`height - item X yAbs - offsety` `（其中item：为实际曲线的数据值）`

## 本人其他开源：
## 全动态插件化框架WXDynamicPlugin介绍文章：

#### [(一) 插件化框架开发背景：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7347994218235363382)

#### [(二）插件化框架主要介绍：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7367676494976532490)

#### [(三）插件化框架内部详细介绍: 零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7368397264026370083)

#### [(四）插件化框架接入详细指南：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7372393698230550565)

#### [(五) 大型项目架构：全动态插件化+模块化+Kotlin+协程+Flow+Retrofit+JetPack+MVVM+极限瘦身+极限启动优化+架构示例+全网唯一](https://juejin.cn/post/7381787510071934985)

#### [(六) 大型项目架构：解析全动态插件化框架WXDynamicPlugin是如何做到全动态化的？](https://juejin.cn/post/7388891131037777929)

#### [(七) 还在不断升级发版吗？从0到1带你看懂WXDynamicPlugin全动态插件化框架？](https://juejin.cn/post/7412124636239904819)

#### [(八) Compose插件化：一个Demo带你入门Compose，同时带你入门插件化开发](https://juejin.cn/post/7425434773026537483)

#### [(九) 花式高阶：插件化之Dex文件的高阶用法，极少人知道的秘密 ](https://juejin.cn/spost/7428216743166771212)

#### [(十) 5种常见Android的SDK开发的方式，你知道几种？ ](https://juejin.cn/post/7431088937278947391)

#### [(十一) 5种WebView混合开发动态更新方式，直击痛点，有你想要的？ ](https://juejin.cn/post/7433288965942165558)

#### [(十二) Compose的全动态插件化框架支持了，已更新到AGP 8.6,Kotlin2.0.20,支持Compose](https://juejin.cn/post/7435587382345482303)

## 本人其他开源：

#### [那些大厂架构师是怎样封装网络请求的？](https://juejin.cn/post/7435904232597372940)

#### [Kotlin+协程+Flow+Retrofit+OkHttp这么好用，不运行安装到手机可以调试接口吗?可以自己搭建一套网络请求工具](https://juejin.cn/post/7406675078810910761)

#### [花式封装：Kotlin+协程+Flow+Retrofit+OkHttp +Repository，倾囊相授,彻底减少模版代码进阶之路](https://juejin.cn/post/7417847546323042345)

#### [注解处理器在架构，框架中实战应用：MVVM中数据源提供Repository类的自动生成](https://juejin.cn/post/7392258195089162290)


#### 感谢阅读，欢迎给给个星，你们的支持是我开源的动力

## 欢迎光临：

#### **[我的掘金地址](https://juejin.cn/user/356661835082573)**

#### 关于我

**VX号：wgllss**  ,如果想更多交流请加我VX