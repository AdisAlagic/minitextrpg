package com.adisalagic.minitextrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adisalagic.minitextrpg.locations.Garden
import com.adisalagic.minitextrpg.ui.theme.MiniTextRPGTheme
import com.adisalagic.minitextrpg.ui.theme.colors
import com.adisalagic.minitextrpg.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val data by mainViewModel.state.collectAsState()
            MiniTextRPGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .padding(5.dp)
                                .border(2.dp, colors.onBackground),
                        ) {
                            Column {

                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Actions(data: MainViewModel.Storage) {
    val state = rememberLazyGridState()
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        state = state,
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when(data.state) {
            MainViewModel.State.CHATTING -> TODO()
            MainViewModel.State.BATTLE -> TODO()
            MainViewModel.State.EXPLORING -> {
                val location = data.currentLocation
                items(location.options) {
                    Button(onClick = {
                        it.use()
                    }) {
                        Text(it)
                    }
                }
            }
        }
    }
}