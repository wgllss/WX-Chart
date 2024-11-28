package com.wx.chart

import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.wx.chart.data.ChartBarModel
import com.wx.chart.data.ChartPieModel
import com.wx.chart.data.ChatLineModel
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun realDrawLineChart(modifier: Modifier, textMeasurer: TextMeasurer, it: ChatLineModel, touchIndex: Int, isTouchLast: Boolean, isBesselLine: Boolean = true) {
    var mSize by remember { mutableStateOf(Size(0f, 0f)) }

    val width = mSize.width
    val height = mSize.height
    val heightDiv = (height - 2 * it.offsety) / it.yCount
    val yValue = it.maxY / (it.yCount)
    val yAbs = (height - 2 * it.offsety) / it.maxY
    val xDiv = (width - 1.5f * it.offsetx) / (it.xCount - 1)

    var animatedloadPos by remember { mutableStateOf(1) }
    LaunchedEffect(Unit) {
        delay(it.animateDelay)
        animatedloadPos = 1
    }
    val endXValue by animateFloatAsState(targetValue = xDiv * animatedloadPos, animationSpec = tween(it.durationMillis))

    Canvas(modifier = modifier) {
        mSize = size
        if (it.isShowYLine)
        //绘制 Y轴
            drawLine(
                start = Offset(it.offsetx, height - it.offsety), end = Offset(it.offsetx, it.offsety), color = Color.Black, strokeWidth = 2f
            )
        //绘制 X轴
        drawLine(
            start = Offset(it.offsetx, height - it.offsety), end = Offset(width - it.offsetx / 2, height - it.offsety), color = Color.Black, strokeWidth = 2f
        )

        for (i in 0..it.yCount) {
            drawLine(
                start = Offset(it.offsetx, height - it.offsety - i * heightDiv), end = Offset(width - it.offsetx / 2, height - it.offsety - i * heightDiv), color = Color.LightGray, strokeWidth = 1f
            )
            drawText(
                textMeasurer = textMeasurer, text = "${"%.2f".format((i * yValue))}", topLeft = Offset(it.offsetxLable, height - it.offsety - i * heightDiv - it.offsetyLable)
            )
        }
        for (i in 0 until it.xCount step it.xLableStep) {
            drawText(
                textMeasurer = textMeasurer, text = "${it.listX[i]}", topLeft = Offset(it.offsetx + i * xDiv - 30f, height - 3 * it.offsetyLable)
            )
        }
        val path = Path()
        for ((m, model) in it.datas.withIndex()) {
            for ((index, item) in model.listY.withIndex()) {
                if (index == 0) {
                    path.moveTo((it.offsetx), height - item * yAbs - it.offsety)
                }
                if (isBesselLine) {
                    if (index + 1 < model.listY.size) {
                        //贝塞尔曲线画法
                        val nextItem = model.listY[index + 1]

                        path.cubicTo(it.offsetx + index * endXValue, height - item * yAbs - it.offsety, it.offsetx + index * endXValue + endXValue / 4, height - item * yAbs - it.offsety, it.offsetx + index * endXValue + endXValue / 2, height - item * yAbs - it.offsety + (item - nextItem) / 2 * yAbs)
                        path.cubicTo(
                            it.offsetx + index * endXValue + endXValue / 2,
                            height - item * yAbs - it.offsety + (item - nextItem) / 2 * yAbs,
                            it.offsetx + index * endXValue + 3 * endXValue / 4,
                            height - nextItem * yAbs - it.offsety,
                            it.offsetx + (index + 1) * endXValue,
                            height - nextItem * yAbs - it.offsety
                        )
                    }
                } else {
                    //下面2行没有贝塞尔曲线画法
                    path.lineTo((it.offsetx + index * endXValue), height - item * yAbs - it.offsety)
                    path.moveTo((it.offsetx + index * endXValue), height - item * yAbs - it.offsety)
                }
            }
            //绘制path
            drawPath(
                path = path, color = model.color, style = Stroke(width = 2f)
            )
            path.reset()

            touchIndex.takeIf {
                it != -1
            }?.let { i ->
                drawOval(
                    color = model.color, topLeft = Offset(it.offsetx + i * xDiv - 8f, height - it.offsety - model.listY[i] * yAbs - 8f), size = Size(16f, 16f)
                )
                if (isTouchLast) {
                    drawText(
                        textMeasurer = textMeasurer, text = "${model.lineTitle}:${"%.2f".format(model.listY[i])}", style = TextStyle(color = model.color), topLeft = Offset(it.offsetx + i * xDiv - 2 * it.offsetx, (m + 2.5f) * it.offsety)
                    )
                } else {
                    drawText(
                        textMeasurer = textMeasurer, text = "${model.lineTitle}:${"%.2f".format(model.listY[i])}", style = TextStyle(color = model.color), topLeft = Offset(it.offsetx + i * xDiv + 18f, (m + 2.5f) * it.offsety)
                    )
                }
            }
        }
        path.close()

        touchIndex.takeIf {
            it != -1
        }?.let { t ->
            if (isTouchLast) {
                drawRect(
                    it.clickLayerColor, size = Size(2 * it.offsetx, (it.datas.size + 1.5f) * it.offsety), topLeft = Offset(it.offsetx + t * xDiv - 2 * it.offsetx - 10f, it.offsety)
                )
                drawText(
                    textMeasurer = textMeasurer, text = "${it.listX[t]}", style = TextStyle(color = Color.Black), topLeft = Offset(it.offsetx + t * xDiv - 2 * it.offsetx, 1.5f * it.offsety)
                )
            } else {
                drawRect(
                    it.clickLayerColor, size = Size(2 * it.offsetx, (it.datas.size + 1.5f) * it.offsety), topLeft = Offset(it.offsetx + t * xDiv + 10f, it.offsety)
                )
                drawText(
                    textMeasurer = textMeasurer, text = "${it.listX[t]}", style = TextStyle(color = Color.Black), topLeft = Offset(it.offsetx + t * xDiv + 18f, 1.5f * it.offsety)
                )
            }
            drawLine(
                start = Offset(it.offsetx + t * xDiv, height - it.offsety), end = Offset(it.offsetx + t * xDiv, it.offsety), color = Color.Black, strokeWidth = 2f
            )
        }
    }
}

fun getTouchIndex(chatModel: ChatLineModel?, width: Float, x: Float, y: Float): Int {
    chatModel?.let {
        val xValue = (width - it.offsetx) / it.xCount
        for (chatLinebean in it.datas) {
            for (i in 0 until chatLinebean.listY.size) {
                if (x > it.offsetx + i * xValue - xValue / 2 && x < it.offsetx + i * xValue + xValue / 2) {
                    return i
                }
            }
        }
    }
    return -1
}

fun getTouchBarIndex(chatModel: ChartBarModel?, width: Float, x: Float, y: Float): Int {
    chatModel?.let {
        val xValue = (width - 1.5f * it.offsetx - 3 * it.barWidth) / (it.xCount - 1)
        for (i in 0 until it.list.size) {
            if (x > it.offsetx + it.barWidth + i * xValue - xValue / 2 && x < it.offsetx + it.barWidth + i * xValue + xValue / 2) {
                return i
            }
        }
    }
    return -1
}

@Composable
fun realDrawBarChart(modifier: Modifier, textMeasurer: TextMeasurer, it: ChartBarModel, touchIndex: Int, isTouchLast: Boolean) {
    var mSize by remember { mutableStateOf(Size(0f, 0f)) }

    val width = mSize.width
    val height = mSize.height
    val heightDiv = (height - 2 * it.offsety) / it.yCount
    val yValue = it.maxY / (it.yCount)
    val yAbs = (height - 2 * it.offsety) / it.maxY
    val xDiv = (width - 1.5f * it.offsetx - 3 * it.barWidth) / (it.xCount - 1)

    var start by remember { mutableStateOf(false) }
    val animatedloadPos by animateFloatAsState(targetValue = if (start) 1f else 0f, animationSpec = FloatTweenSpec(it.durationMillis))
    LaunchedEffect(Unit) {
        delay(it.animateDelay)
        start = true
    }
    Canvas(modifier = modifier) {
        mSize = size
        if (it.isShowYLine)
        //绘制 Y轴
            drawLine(
                start = Offset(it.offsetx, height - it.offsety), end = Offset(it.offsetx, it.offsety), color = Color.Black, strokeWidth = 2f
            )
        //绘制 X轴
        drawLine(
            start = Offset(it.offsetx, height - it.offsety), end = Offset(width - it.offsetx / 2, height - it.offsety), color = Color.Black, strokeWidth = 2f
        )

        for (i in 0..it.yCount) {
            //绘制y轴上几条横线
            drawLine(
                start = Offset(it.offsetx, height - it.offsety - i * heightDiv), end = Offset(width - it.offsetx / 2, height - it.offsety - i * heightDiv), color = Color.LightGray, strokeWidth = 1f
            )
            //绘制y轴上几条横线对应的y值
            drawText(
                textMeasurer = textMeasurer, text = "${"%.2f".format((i * yValue))}", topLeft = Offset(it.offsetxLable, height - it.offsety - i * heightDiv - it.offsetyLable)
            )
        }
        for (i in 0 until it.xCount step it.xLableStep) {
            //绘制x上刻度文案
            drawText(
                textMeasurer = textMeasurer, text = "${it.list[i].title}", topLeft = Offset(it.offsetx + i * xDiv + it.barWidth - 30f, height - 3 * it.offsetyLable)
            )
        }
        for ((m, item) in it.list.withIndex()) {
            //绘制条型柱状
            drawRect(
                color = item.color, topLeft = Offset(it.offsetx + it.barWidth + m * xDiv, height - it.offsety - item.value * yAbs * animatedloadPos), size = Size(it.barWidth, item.value * yAbs * animatedloadPos)
            )
        }
        touchIndex.takeIf {
            it != -1
        }?.let { t ->
            val offsetY = if (it.list[t].value * yAbs < 1.5f * it.offsety) 1.5f * it.offsety else it.list[t].value * yAbs
            if (isTouchLast) {
                drawRect(
                    it.clickLayerColor, size = Size(it.layerWidth, 1.5f * it.offsety), topLeft = Offset(it.offsetx + it.barWidth + t * xDiv - it.layerWidth - 10f, height - it.offsety - offsetY - 0.75f * it.offsety)
                )
                drawText(
                    textMeasurer = textMeasurer, text = "${it.list[t].title}:${it.list[t].value}", style = TextStyle(color = it.list[t].color), topLeft = Offset(it.offsetx + it.barWidth + t * xDiv - it.layerWidth, height - it.offsety - offsetY - 0.75f * it.offsety + 0.5f * it.offsety)
                )
            } else {
                drawRect(
                    it.clickLayerColor, size = Size(it.layerWidth, (1.5f) * it.offsety), topLeft = Offset(it.offsetx + it.barWidth + t * xDiv + 10f, height - it.offsety - offsetY - 0.75f * it.offsety)
                )
                drawText(
                    textMeasurer = textMeasurer, text = "${it.list[t].title}:${it.list[t].value}", style = TextStyle(color = it.list[t].color), topLeft = Offset(it.offsetx + it.barWidth + t * xDiv + 18f, height - it.offsety - offsetY - 0.75f * it.offsety + 0.5f * it.offsety)
                )
            }
        }
    }
}

@Composable
fun realDrawPieChart(modifier: Modifier, textMeasurer: TextMeasurer, it: ChartPieModel, touchPieData: Triple<Int, Float, Float>) {
    var mSize by remember { mutableStateOf(Size(0f, 0f)) }
    val centerX = mSize.center.x
    val centerY = mSize.center.y
    val width = mSize.width
    val hight = mSize.height
    val radius = min(centerX - it.radiusOffset, centerY - it.radiusOffset) //饼图半径
    val rateAbs = 360f / it.total
    val listSize = it.list.size

    val ydiv = 2 * (radius + if (it.isHoop) it.hoopSize / 2f else 0f) / listSize
    var prefixAngle = 0f

    val listAngle = remember { mutableListOf<Float>() }
    var pieState by remember { mutableStateOf(false) }
    val pieAnimateList = remember { Array(listSize) { 0f } }
    LaunchedEffect(true) {
        delay(it.animateDelay)
        pieState = true
    }

    var delayDuration = 0
    for (index in 0 until listSize) {
        val duration = (it.durationMillis * it.list[index].value / it.total).toInt()
        val diff by animateFloatAsState(
            if (pieState) it.list[index].value * rateAbs else 0f, animationSpec = TweenSpec(
                duration, easing = LinearEasing, delay = delayDuration
            )
        )
        delayDuration += duration
        pieAnimateList[index] = diff
    }
    val animate = remember { pieAnimateList }

    Canvas(modifier) {
        mSize = size
        for (i in 0 until listSize) {
            drawArc(
                color = it.list[i].color, useCenter = !it.isHoop, startAngle = -90f + prefixAngle, sweepAngle = animate[i], topLeft = Offset(centerX - radius + it.offsetx, centerY - radius + it.offsety), size = Size(2 * radius, 2 * radius), style = if (it.isHoop) Stroke(it.hoopSize) else Fill
            )
            listAngle.add(prefixAngle)
            prefixAngle += rateAbs * it.list[i].value

            drawOval(
                color = it.list[i].color, topLeft = Offset(
                    min(width, centerX + radius + it.offsetx + it.offsetxLable + if (it.isHoop) it.hoopSize else 0f), min(hight, centerY - radius - it.hoopSize / 2 + i * ydiv + (ydiv - 16f) / 2)
                ), size = Size(ydiv / 2, ydiv / 2)
            )

            drawText(
                textMeasurer = textMeasurer,
                text = "${it.list[i].name}:${it.list[i].value}",
                style = TextStyle(color = it.list[i].color),
                topLeft = Offset(min(width, centerX + radius + it.offsetx + it.offsetxLable + (if (it.isHoop) it.hoopSize else 0f) + ydiv), min(hight, centerY - radius - it.hoopSize / 2 + i * ydiv + (ydiv - 16f) / 2))
            )
        }
        touchPieData.takeIf {
            it.first != -1
        }?.let { t ->
            drawArc(
                color = it.list[t.first].color,
                useCenter = false,
                startAngle = -90f + listAngle[t.first],
                sweepAngle = it.list[t.first].value * rateAbs,
                topLeft = Offset(min(width, centerX - radius + it.offsetx), min(hight, centerY - radius + it.offsety)),
                size = Size(2 * radius, 2 * radius),
                style = Stroke(it.scaleOffset + if (it.isHoop) it.hoopSize else 0f)
            )

            drawText(
                textMeasurer, text = "${it.list[t.first].name}:${it.list[t.first].value}", style = TextStyle(color = it.list[t.first].color), topLeft = Offset(
                    min(width, t.second), min(hight, t.third)
                )
            )
        }
    }
}

//得到第一 第二 第三 第四象限，以圆心坐标为原点计算，数学公式
fun touchOnWhichPart(centerX: Float, centerY: Float, x: Float, y: Float): Int {
    return if (x > centerX) {
        if (y > centerY) 4 else 1
    } else if (y > centerY) 3 else 2
}

fun getTouchPieIndex(chatModel: ChartPieModel?, centerX: Float, centerY: Float, x: Float, y: Float): Triple<Int, Float, Float> {
    chatModel?.let {
        val radius = min(centerX - it.radiusOffset, centerY - it.radiusOffset) //饼图半径
        val hoopSize = if (it.isHoop) radius else 0f
        val distance = (x - (centerX + it.offsetx)) * (x - (centerX + it.offsetx)) + (y - (centerY + it.offsety)) * (y - (centerY + it.offsety))
        val outDistance = (radius + hoopSize / 2) * (radius + hoopSize / 2)
        val interDistance = if (it.isHoop) (radius - hoopSize / 2) * (radius - hoopSize / 2) else 0f
        var alfa = 0.00
        if (distance < outDistance && distance > interDistance) {
            val type = touchOnWhichPart(centerX + it.offsetx, centerY + it.offsety, x, y)
            when (type) {
                1 -> {
                    alfa = Math.atan2((x - (centerX + it.offsetx)).toDouble(), ((centerY + it.offsety) - y).toDouble()) * 180 / PI;
                }

                2 -> {
                    alfa = Math.atan2(((centerY + it.offsety) - y).toDouble(), ((centerX + it.offsetx) - x).toDouble()) * 180 / PI + 270;
                }

                3 -> {
                    alfa = Math.atan2(((centerX + it.offsetx) - x).toDouble(), (y - (centerY + it.offsety)).toDouble()) * 180 / PI + 180;
                }

                4 -> {
                    alfa = Math.atan2((y - (centerY + it.offsety)).toDouble(), (x - (centerX + it.offsetx)).toDouble()) * 180 / PI + 90;
                }

                else -> {

                }
            }
            val rateAbs = 360f / it.total
            val listSize = it.list.size
            var prefixAngle = 0f
            if (alfa >= 0) {
                for (i in 0 until listSize) {
                    val g = prefixAngle + rateAbs * it.list[i].value / 2//角度
                    val addDif = if (it.isHoop) it.hoopSize / 2 + 2 * it.scaleOffset else 2 * it.scaleOffset

                    prefixAngle += rateAbs * it.list[i].value
                    if (alfa > prefixAngle) {

                    } else {
                        var offsetX = 0f
                        var offsetY = 0f
                        when (type) {
                            1 -> {
                                val q = Math.toRadians(g.toDouble())//角度
                                offsetX = centerX + it.offsetx + (radius + addDif) * sin(q).toFloat()
                                offsetY = centerY + it.offsety - (radius + addDif) * cos(q).toFloat()
                            }

                            2 -> {
                                val q = Math.toRadians((g - 270f).toDouble())//角度
                                offsetX = centerX + it.offsetx - (radius + addDif) * cos(q).toFloat() - 2 * it.scaleOffset - it.lableLeftOffetx
                                offsetY = centerY + it.offsety - (radius + addDif) * sin(q).toFloat()
                            }

                            3 -> {
                                val q = Math.toRadians((g - 180f).toDouble())//角度
                                offsetX = centerX + it.offsetx - (radius + addDif) * sin(q).toFloat() - 2 * it.scaleOffset - it.lableLeftOffetx
                                offsetY = centerY + it.offsety + (radius + addDif) * cos(q).toFloat()
                            }

                            4 -> {
                                val q = Math.toRadians((g - 90f).toDouble())//角度
                                offsetX = centerX + it.offsetx + (radius + addDif) * cos(q).toFloat()
                                offsetY = centerY + it.offsety + (radius + addDif) * sin(q).toFloat()
                            }
                        }
                        return Triple(i, offsetX, offsetY)
                    }
                }
            }
        }
    }
    return Triple(-1, 0f, 0f)
}