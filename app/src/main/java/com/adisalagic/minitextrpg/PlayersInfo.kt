package com.adisalagic.minitextrpg

class PlayersInfo {

    // Method calls will be added by gradle task hideSecret
    // Example : external fun getWellHiddenSecret(packageName: String): String

    companion object {
        init {
            System.loadLibrary("secrets")
        }
    }

    external fun getanbshagndshgnwiahgfnehfgje(packageName: String): String
}