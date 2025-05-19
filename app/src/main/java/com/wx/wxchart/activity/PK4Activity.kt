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


class PK4Activity : ComponentActivity() {
    val viewModel by viewModels<DynamicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
//                    baseUI2({ paddingvalues ->
                    dynamicVSstyle2Chart(viewModel)
//                    }, {
//                        finish()
//                    })
                }
            }
        }
        viewModel.setData6()
    }
}

@Composable
fun dynamicVSstyle2Chart(viewModel: DynamicViewModel = DynamicViewModel().apply { setData6() }) {
    val textMeasurer = rememberTextMeasurer()
    var bitmapBg by remember { mutableStateOf(createImageBitmap()) }
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val chatModel by viewModel.dynamicPKModel6.observeAsState()

    LaunchedEffect(Unit) {
        chatModel?.bgUrl?.takeIf {
            it.isNotEmpty()
        }.let {
            Glide.with(context).asBitmap().load(it).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapBg = resource.asImageBitmap()
                }
            })
        }
    }
    chatModel?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .onSizeChanged {
                    width = it.width
                    height = it.height
                }
                .drawBehind {
                    drawImage(
                        bitmapBg, srcOffset = IntOffset.Zero, srcSize = IntSize(bitmapBg.width, bitmapBg.height),   //绘制的图片大小
                        dstOffset = IntOffset.Zero, dstSize = IntSize(
                            width, height
                        )
                    )
                }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 25.dp, 0.dp, 0.dp)
                    .height(60.dp), verticalAlignment = Alignment.CenterVertically,   //整体垂直居中
                horizontalArrangement = Arrangement.Center                 //整体水平居中
            ) {
                Text(text = it.pkLeftName, fontSize = 30.sp, color = Color.Red)
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp), model = it.pkLeftImgUrl, contentDescription = "", contentScale = ContentScale.Crop
                )
                Text(text = "VS", fontSize = 36.sp, color = Color.White, modifier = Modifier.width(110.dp), textAlign = TextAlign.Center)
                AsyncImage(
                    modifier = Modifier.size(60.dp), model = it.pkRightImgUrl, contentDescription = "", contentScale = ContentScale.Crop
                )
                Text(text = it.pkRightName, fontSize = 30.sp, color = Color.Magenta, modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
            }
            val modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(0x70000000))
            com.wx.chart.dynamicVSstyle2Chart(
                modifier, textMeasurer, TextStyle(
                    fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.White
                ), TextStyle(
                    fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.White
                ), it
            )
        }
    }
}







