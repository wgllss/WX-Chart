package com.wx.wxchart.activity

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.wx.chart.drawDynamicTopBarChart
import com.wx.chart.utils.DefaultBitmapUtils.createImageBitmap
import com.wx.wxchart.composable.baseUI2
import com.wx.wxchart.theme.WXChart
import com.wx.wxchart.viewmodel.DynamicViewModel
import kotlinx.coroutines.launch

class DynamicTopBarActivity : ComponentActivity() {
    val viewModel by viewModels<DynamicViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
//                    baseUI2({ paddingvalues ->
                    dynamicTopBarChart(viewModel = viewModel)
//                    }, {
//                        finish()
//                    })
                }
            }
        }
        viewModel.setData2()
    }
}


@Composable
fun dynamicTopBarChart(innerPadding: PaddingValues = PaddingValues(0.dp), viewModel: DynamicViewModel = DynamicViewModel().apply { setData2() }) {
    val textMeasurer = rememberTextMeasurer()
    val context = LocalContext.current
    val chatModel by viewModel.dynamicTopModel.observeAsState()
    var bitmapBg by remember { mutableStateOf(createImageBitmap()) }
    var width by remember { mutableStateOf(0) }
    var height by remember { mutableStateOf(0) }

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
                .padding(innerPadding)
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
                    .height(40.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
            ) {
                Text(text = it.dynamicChartName, fontSize = 22.sp, color = Color.White, style = TextStyle.Default)
            }


            val modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            drawDynamicTopBarChart(
                modifier, textMeasurer, TextStyle(
                    fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color.Red
                ), it
            ) {}
        }
    }
}

