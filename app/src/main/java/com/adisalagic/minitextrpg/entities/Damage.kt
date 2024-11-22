package com.adisalagic.minitextrpg.entities

import com.adisalagic.minitextrpg.minus
import com.adisalagic.minitextrpg.percent
import kotlin.random.Random

class Damage(val minDamage: Long, val maxDamage: Long, private val random: Random = Random) {

    constructor(maxDamage: Long, random: Random = Random) : this(maxDamage - 10.percent, maxDamage, random)

    fun rollDamage(): Long {
        return random.nextLong(minDamage, maxDamage + 1)
    }



}