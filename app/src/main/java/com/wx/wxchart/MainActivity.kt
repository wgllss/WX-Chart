package com.wx.wxchart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.wx.chart.getTouchIndex
import com.wx.chart.realDrawChart
import com.wx.wxchart.composable.baseUI
import com.wx.wxchart.theme.WXChart
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    val viewModel by viewModels<SampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
                    baseUI { paddingvalues ->
                        LineChart(paddingvalues, viewModel)
                    }
                }
            }
        }
        viewModel.setData()
    }
}


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

@Preview
@Composable
fun GreetingPreview() {
    WXChart {
        LineChart(PaddingValues(0.dp))
    }
}



