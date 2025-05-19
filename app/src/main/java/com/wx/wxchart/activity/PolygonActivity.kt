package com.wx.wxchart.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.wx.chart.drawPolygonChart
import com.wx.wxchart.composable.baseUI2
import com.wx.wxchart.theme.WXChart
import com.wx.wxchart.viewmodel.PolygonViewModel
import kotlinx.coroutines.launch


class PolygonActivity : ComponentActivity() {
    val viewModel by viewModels<PolygonViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
                    baseUI2({ paddingvalues ->
                        polygonChart(paddingvalues, viewModel)
                    }, {
                        finish()
                    })
                }
            }
        }
        viewModel.setData()
    }
}

@Composable
fun polygonChart(innerPadding: PaddingValues, polygonViewModel: PolygonViewModel) {
    val textMeasurer = rememberTextMeasurer()
    val chatModel by polygonViewModel.polygonModel.observeAsState()
    chatModel?.let {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            val modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
            drawPolygonChart(
                modifier = modifier, textMeasurer, TextStyle(
                    fontSize = 20.sp, fontWeight = FontWeight.Normal, color = Color.Black
                ), it
            )
        }
    }
}






