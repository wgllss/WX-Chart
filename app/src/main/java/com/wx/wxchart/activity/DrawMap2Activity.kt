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


class DrawMap2Activity : ComponentActivity() {
    val viewModel by viewModels<MapViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
                    baseUI2({ paddingvalues ->
                        drawMapImg(paddingvalues, viewModel)
                    }, {
                        finish()
                    })
                }
            }
        }
        viewModel.setData2()
    }
}


@Composable
fun drawMapImg(innerPadding: PaddingValues = PaddingValues(0.dp), viewDModel33: MapViewModel = MapViewModel().apply { setData2() }) {
    val mapList by viewDModel33.mapList.observeAsState(emptyList())
    LazyVerticalGrid(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(), columns = GridCells.Fixed(6), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        mapList?.let {
            items(it) { item ->
                Image(
                    painter = painterResource(item), contentDescription = "map ", modifier = Modifier
                        .background(Color.Red)
                        .width(226.dp)
                        .height(226.dp)
                )

//                val bitmap = imageResource(vectorResId = R.drawable.sichuan)
//                Canvas(
//                    modifier = Modifier
//                        .width(226.dp)
//                        .height(226.dp)
//                ) {
//                    drawImage(bitmap, dstOffset = IntOffset(360, 250), dstSize = IntSize(226, 226))
//                }
            }
        }
    }
}

@Composable
fun imageResource(vectorResId: Int): ImageBitmap {
    val context = LocalContext.current
    val vectorDrawable = context.getDrawable(vectorResId) as VectorDrawable
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap.asImageBitmap()
}





