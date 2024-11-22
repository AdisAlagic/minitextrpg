package com.adisalagic.minitextrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
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
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adisalagic.minitextrpg.entities.Item
import com.adisalagic.minitextrpg.entities.Weapon
import com.adisalagic.minitextrpg.ui.theme.MiniTextRPGTheme
import com.adisalagic.minitextrpg.ui.theme.colors
import com.adisalagic.minitextrpg.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            initUtils()
            val data by mainViewModel.state.collectAsState()
            val scrollableState = rememberScrollState()
            val coroutine = rememberCoroutineScope()
            var dialogItem: Item? by remember { mutableStateOf(null) }
            MiniTextRPGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        LaunchedEffect(data.currentLocation) {
                            mainViewModel.showEnter()
                        }
                        LaunchedEffect(data.currentText) {
                            coroutine.launch { scrollableState.animateScrollBy(3000f) }
                        }
                        if (data.enemy.currentHealth.currentHealth <= 0) {
                            Box(Modifier.fillMaxSize(), Alignment.Center) {
                                Text(
                                    "Вы победили\n${
                                        PlayersInfo().getanbshagndshgnwiahgfnehfgje(
                                            this@MainActivity.packageName
                                        )
                                    }",
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else if (data.player.health.currentHealth <= 0) {
                            Box(Modifier.fillMaxSize(), Alignment.Center) {
                                Text("Вы проиграли")
                            }
                        } else {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight(0.5f)
                                        .fillMaxWidth()
                                        .border(2.dp, colors.onBackground)
                                ) {
                                    Column(
                                        Modifier
                                            .verticalScroll(scrollableState)
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                    ) {
                                        Text(data.currentText)
                                    }
                                }
                                Actions(data)
                                PlayerInventory(data) {
                                    dialogItem = it
                                    false
                                }
                            }
                        }
                    }
                    if (dialogItem != null) {
                        AlertDialog(
                            onDismissRequest = { dialogItem = null },
                            confirmButton = {
                                TextButton(onClick = { mainViewModel.useItem(dialogItem!!); dialogItem = null }) {
                                    Text("Использовать")
                                }
                            },
                            title = { Text(dialogItem!!.name) },
                            text = {
                                Text(
                                    dialogItem!!.description + "\n${
                                        if (dialogItem!! is Weapon) "Экипировать?" else "Использовать?"
                                    }"
                                )
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    dialogItem = null
                                }) { Text("Отмена") }
                            },
                        )
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
        modifier = Modifier.height(100.dp),
        rows = GridCells.Fixed(2),
        state = state,
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when (data.state) {
            MainViewModel.State.CHATTING -> TODO()
            MainViewModel.State.BATTLE -> {
                if (data.playersTurn == MainViewModel.Turn.PLAYER) {
                    item {
                        Button(
                            onClick = { mainViewModel.attack() },
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text("Атаковать")
                        }
                    }
                    item {
                        Button(
                            onClick = { mainViewModel.defend() },
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text("Защититься")
                        }
                    }
                    item {
                        Button(
                            onClick = { mainViewModel.skipTurn() },
                            modifier = Modifier.width(150.dp)
                        ) {
                            Text("Пропустить")
                        }
                    }
                } else if (data.playersTurn == MainViewModel.Turn.MIDDLE) {
                    item {
                        Button(onClick = { mainViewModel.nextTurn()}) {
                            Text("Следующий ход")
                        }
                    }
                }
            }

            MainViewModel.State.EXPLORING -> {
                val location = data.currentLocation
                items(location.options) {
                    Button(onClick = {
                        it.use()
                    }, modifier = Modifier.width(150.dp)) {
                        Text(it.action)
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerInventory(data: MainViewModel.Storage, onUse: (Item) -> Boolean) {
    val state = rememberLazyGridState()
    Column {
        Text(text = "Инвентарь", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        LazyHorizontalGrid(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .border(1.dp, colors.onBackground),
            rows = GridCells.Fixed(2),
            state = state,
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(data.player.playerInventory) {
                TextButton(
                    enabled = (data.state == MainViewModel.State.BATTLE && data.playersTurn == MainViewModel.Turn.PLAYER) ||
                            data.state == MainViewModel.State.EXPLORING,
                    onClick = { onUse(it) }
                ) {
                    Text(it.name)
                }
            }
        }
    }
}