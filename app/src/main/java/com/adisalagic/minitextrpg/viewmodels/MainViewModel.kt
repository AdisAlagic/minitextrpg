package com.adisalagic.minitextrpg.viewmodels

import androidx.lifecycle.ViewModel
import com.adisalagic.minitextrpg.entities.Player
import com.adisalagic.minitextrpg.locations.Garden
import com.adisalagic.minitextrpg.locations.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(Storage())
    val state = _state.asStateFlow()

    private val data get() = _state.value

    init {
        _state.update { it.copy(currentLocation = it.currentLocation.apply { onOptionClicked = { opt ->
            when(opt.action) {
                
            }
        } }) }
    }

    data class Storage(
        val state: State = State.EXPLORING,
        val currentLocation: Location = Garden(),
        val player: Player = Player()
    )

    enum class State {
        CHATTING,
        BATTLE,
        EXPLORING
    }
}