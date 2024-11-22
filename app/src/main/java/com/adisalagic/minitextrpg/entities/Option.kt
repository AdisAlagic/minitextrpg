package com.adisalagic.minitextrpg.entities

data class Option(val id: Int, val action: String, val onClicked: (Option) -> Unit) {
    fun use() = onClicked(this)
}