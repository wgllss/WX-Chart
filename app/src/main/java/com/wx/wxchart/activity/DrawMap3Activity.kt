package com.wx.wxchart.activity

import android.graphics.Bitmap
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.wx.chart.getTouchIndex
import com.wx.chart.realDrawLineChart
import com.wx.chart.utils.DisplayUtil
import com.wx.chart.utils.StringUtils.getStrPhysicsLength
import com.wx.chart.wxDrawMap
import com.wx.wxchart.R
import com.wx.wxchart.viewmodel.SampleViewModel
import com.wx.wxchart.composable.baseUI
import com.wx.wxchart.composable.baseUI2
import com.wx.wxchart.theme.WXChart
import com.wx.wxchart.viewmodel.MapViewModel
import kotlinx.coroutines.launch


class DrawMap3Activity : ComponentActivity() {
    val viewModel by viewModels<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
                    baseUI2({ paddingvalues ->
                        drawMap3(paddingvalues, viewModel)
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
fun drawMap3(innerPadding: PaddingValues = PaddingValues(0.dp), viewDModel33: MapViewModel = MapViewModel().apply { setData3() }) {
    val context = LocalContext.current
    val data by viewDModel33.mapData.observeAsState()
    val textMeasurer = rememberTextMeasurer()
    data?.let {
        Box(modifier = Modifier.padding(innerPadding)) {
            wxDrawMap(
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight(), it
            ) { drawScope, x, y, abs, data ->
                val newX = x * abs  //计算出点击屏幕真实
                val newY = y * abs  //计算出点击屏幕真实
                val fontSizeDip = DisplayUtil.sp2Dip(context, 16.sp.value)
                val textWidth = getStrPhysicsLength(data.name) * fontSizeDip
                drawScope.drawRect(
                    it.clickLayerColor, size = Size((textWidth + 2 * fontSizeDip).toFloat(), 4 * fontSizeDip.toFloat()), topLeft = Offset(newX, newY)
                )
                //xml 里面  android:name="" 没有，所以点击事件绘制文案没有，需要自行配置
                drawScope.drawText(
                    textMeasurer = textMeasurer, text = data.name, style = TextStyle(color = Color.White, fontSize = 16.sp), topLeft = Offset(newX + fontSizeDip, newY + 10f)
                )
            }
        }
    }
}





