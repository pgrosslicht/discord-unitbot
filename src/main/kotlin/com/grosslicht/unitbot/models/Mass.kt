package com.grosslicht.unitbot.models

import com.grosslicht.unitbot.utils.format

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

data class Mass(val grams: Double): Convertible {
    val ounce: Double
    val pound: Int
    init {
        pound = (grams * 0.035274).toInt()/16
        ounce = (grams * 0.035274) % 16
    }

    override fun toString(): String {
        var output = when (grams) {
            in (1..1000) -> "${grams.format(2)} g = "
            else -> "${(grams/1000).format(2)} kg = "
        }
        output += when (pound) {
            0 -> "${ounce.format(2)} oz"
            else -> if (ounce > 0.01) { "$pound pounds ${ounce.format(2)} ounces" } else { "$pound pounds" }
        }
        return output
    }
}

