package com.wx.wxchart.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.wx.chart.drawPyramidChart
import com.wx.chart.dynamicPyramidChart
import com.wx.wxchart.composable.baseUI2
import com.wx.wxchart.theme.WXChart
import com.wx.wxchart.viewmodel.PyramidViewModel
import kotlinx.coroutines.launch


class PyramidActivity : ComponentActivity() {
    val viewModel by viewModels<PyramidViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
//                    baseUI2({ paddingvalues ->
                    pyramidChart(viewModel)
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
fun pyramidChart(viewModel: PyramidViewModel = PyramidViewModel().apply { setData2() }) {
    val chatModel by viewModel.pyramidModel2.observeAsState()
    val textMeasurer = rememberTextMeasurer()
    chatModel?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), model = it.bgUrl, contentDescription = "", contentScale = ContentScale.Crop
            )
            val modifier = Modifier
                .fillMaxWidth()
//                .height(600.dp)
                .fillMaxHeight()
//            drawPyramidChart(
//                modifier, textMeasurer, it
//            )
            dynamicPyramidChart(
                modifier, textMeasurer, it
            )
        }
    }
}





