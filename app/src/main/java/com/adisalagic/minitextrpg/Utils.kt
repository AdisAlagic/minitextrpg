package com.adisalagic.minitextrpg

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adisalagic.minitextrpg.entities.Item
import com.adisalagic.minitextrpg.entities.Option
import com.adisalagic.minitextrpg.locations.Options
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

operator fun Long.compareTo(percent: Percentage): Int {
    val calc = this / 100 * percent.percentage
    return this.compareTo(calc)
}


data class ItemDropResult(
    val item: Item,
    val amount: Int
)

data class ItemDrop(
    val item: Item,
    val chance: Double,
    val maxAmount: Int = 1,
    val minAmount: Int = 1,
    val random: Random = Random
) {
    fun drop(): ItemDropResult? {
        val chanced = random.nextDouble(0.0,100.1)
        Log.d("Chance","Drop chance: $chanced < $chance = ${chanced < chance}")
        return if (chanced < chance) ItemDropResult(
            item,
            random.nextInt(
                minAmount, maxAmount + 1
            )
        ) else
            null
    }

    fun dropForSure(): ItemDropResult {
        return ItemDropResult(item, random.nextInt(minAmount, maxAmount))
    }
}

fun <T> MutableList<T>.takeRandom(random: Random = Random): T? {
    return if (isEmpty()) null else removeAt(random.nextInt(0, size))
}

fun Option(optionEnum: Options, action: String, onOptionClick: (Option) -> Unit): Option {
    return Option(optionEnum.ordinal, action, onOptionClick)
}

fun String.append(text: String): String {
    return this + text
}

fun String.appendLine(text: String): String {
    return this.append("\n$text")
}

lateinit var mainViewModel: MainViewModel

@SuppressLint("ComposableNaming")
@Composable
fun initUtils() {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    val processOwner = ProcessLifecycleOwner.get()
    mainViewModel = viewModel<MainViewModel>(viewModelStoreOwner)
}