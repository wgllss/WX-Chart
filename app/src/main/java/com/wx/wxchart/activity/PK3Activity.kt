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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.wx.chart.getTouchIndex
import com.wx.chart.realDrawLineChart
import com.wx.chart.utils.DisplayUtil
import com.wx.chart.utils.StringUtils.getStrPhysicsLength
import com.wx.chart.vSChart
import com.wx.chart.vSWithRoleChart
import com.wx.chart.wxDrawMap
import com.wx.wxchart.R
import com.wx.wxchart.viewmodel.SampleViewModel
import com.wx.wxchart.composable.baseUI
import com.wx.wxchart.composable.baseUI2
import com.wx.wxchart.theme.WXChart
import com.wx.wxchart.viewmodel.DynamicViewModel
import com.wx.wxchart.viewmodel.MapViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PK3Activity : ComponentActivity() {
    val viewModel by viewModels<DynamicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
//                    baseUI2({ paddingvalues ->
                    dynamicVSChart(viewModel)
//                    }, {
//                        finish()
//                    })
                }
            }
        }
        viewModel.setData3()
    }
}


@Composable
fun dynamicVSChart(viewModel: DynamicViewModel = DynamicViewModel().apply { setData3() }) {
    val textMeasurer = rememberTextMeasurer()
    val context = LocalContext.current
    val chatModel by viewModel.dynamicPKModel.observeAsState()
    chatModel?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp), verticalAlignment = Alignment.CenterVertically,   //整体垂直居中
                horizontalArrangement = Arrangement.Center                 //整体水平居中
            ) {
                Text(text = it.pkLeftName, fontSize = 30.sp)
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp), model = it.pkLeftImgUrl, contentDescription = "", contentScale = ContentScale.Crop
                )
                Text(text = "VS", fontSize = 36.sp, modifier = Modifier.width(110.dp), textAlign = TextAlign.Center)
                AsyncImage(
                    modifier = Modifier.size(60.dp), model = it.pkRightImgUrl, contentDescription = "", contentScale = ContentScale.Crop
                )
                Text(text = it.pkRightName, fontSize = 30.sp, modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
            }
            val modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            com.wx.chart.dynamicVSChart(
                modifier, textMeasurer, TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.Black
                ), TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.White
                ), it
            )
        }
    }
}







