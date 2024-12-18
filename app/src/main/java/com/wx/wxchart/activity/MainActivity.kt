package com.wx.wxchart.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.wx.wxchart.composable.baseUI
import com.wx.wxchart.theme.WXChart
import com.wx.wxchart.viewmodel.ComposeViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    val viewModel by viewModels<ComposeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                WXChart {
                    baseUI { paddingvalues ->
                        mainUI(paddingvalues, viewModel)
                    }
                }
            }
        }
        viewModel.initUIData()
    }
}


@Composable
fun mainUI(innerPadding: PaddingValues, viewModel: ComposeViewModel) {
    val context = LocalContext.current
    val mainItmes by viewModel.mainItmes.observeAsState(initial = emptyList())
    LazyVerticalGrid(
        modifier = Modifier
            .padding(innerPadding)
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight(), columns = GridCells.Fixed(2)
    ) {
        items(mainItmes) { item ->
            Button(
                onClick = {
                    context.startActivity(Intent(context, item.clazz))
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(5.dp)
            ) {
                Text(item.title)
            }
        }
    }
}



