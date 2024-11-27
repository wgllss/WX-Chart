package com.wx.wxchart.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.wx.chart.getTouchBarIndex
import com.wx.chart.getTouchIndex
import com.wx.chart.getTouchPieIndex
import com.wx.chart.realDrawBarChart
import com.wx.chart.realDrawLineChart
import com.wx.chart.realDrawPieChart
import com.wx.wxchart.viewmodel.SampleViewModel
import com.wx.wxchart.composable.baseUI
import com.wx.wxchart.composable.baseUI2
import com.wx.wxchart.theme.WXChart
import kotlinx.coroutines.launch


class PieChartActivity : ComponentActivity() {
    val viewModel by viewModels<SampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
                    baseUI2({ paddingvalues ->
                        PieChart(paddingvalues, viewModel)
                    }, {
                        finish()
                    })
                }
            }
        }
        viewModel.setData3()
    }
}

@Composable
fun PieChart(innerPadding: PaddingValues = PaddingValues(100.dp), viewModel: SampleViewModel = SampleViewModel().apply { setData() }) {
    var touchPieData by remember { mutableStateOf(Triple(-1, 0f, 0f)) }
    val textMeasurer = rememberTextMeasurer()
    val chatModel by viewModel.chatPieModel.observeAsState()
    chatModel?.let {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val modifier3 = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0x20000000))
                .graphicsLayer()      //监听手势缩放
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { t ->
                        touchPieData = getTouchPieIndex(it, size.center.x.toFloat(), size.center.y.toFloat(), t.x, t.y)
                    })
                }
            realDrawPieChart(modifier3, textMeasurer, it, touchPieData)
        }
    }
}





