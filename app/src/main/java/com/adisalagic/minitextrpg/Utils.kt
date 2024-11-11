package com.adisalagic.minitextrpg

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adisalagic.minitextrpg.entities.Item
import com.adisalagic.minitextrpg.viewmodels.MainViewModel
import java.time.temporal.TemporalAmount
import kotlin.random.Random

class Percentage(private var perc: Long) {

    val percentage get() = perc

    init {
        if (perc > 100) perc = 100
        if (perc < 0) perc = 0
    }
}

val Int.percent: Percentage
    get() {
        return Percentage(this.toLong())
    }

val Long.percent: Percentage
    get() {
        return Percentage(this)
    }

operator fun Int.minus(percentage: Percentage): Int {
    return this - (this / 100 * percentage.percentage.toInt())
}

operator fun Int.plus(percentage: Percentage): Int {
    return this + (this / 100 * percentage.percentage.toInt())
}

operator fun Long.minus(percentage: Percentage): Long {
    return this - (this / 100 * percentage.percentage)
}

operator fun Long.plus(percentage: Percentage): Long {
    return this + (this / 100 * percentage.percentage)
}

data class ItemDropResult(
    val item: Item,
    val amount: Int
)

data class ItemDrop(
    val item: Item,
    val chance: Double,
    val maxAmount: Int = 2,
    val minAmount: Int = 1,
    val random: Random = Random
) {
    fun drop(): ItemDropResult? {
        return if (random.nextDouble(100.1) > chance) ItemDropResult(
            item,
            random.nextInt(
                minAmount, maxAmount
            )
        ) else
            null
    }

    fun dropForSure(): ItemDropResult {
        return ItemDropResult(item, random.nextInt(minAmount, maxAmount))
    }
}

fun <T> MutableList<T>.takeRandom(random: Random = Random): T {
    return removeAt(random.nextInt(0, size))
}

lateinit var mainViewModel: MainViewModel

@SuppressLint("ComposableNaming")
@Composable
fun initUtils() {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    val processOwner = ProcessLifecycleOwner.get()
    mainViewModel = viewModel<MainViewModel>(viewModelStoreOwner)
}