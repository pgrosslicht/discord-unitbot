package com.grosslicht.unitbot.models

import com.grosslicht.unitbot.utils.format
import mu.KLogging
import java.util.*

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

data class Distance(val meter: Double): Convertible {
    companion object: KLogging()
    var inch: Double
    val foot: Double
    val mile: Double
    init {
        mile = meter * (15625.0/(25146.0*1000.0))
        logger.debug { "$mile" }
        foot = Math.round(meter * 3.280839895).toDouble()
        inch = (meter * 39.37007874) - (foot*12)
    }

    override fun toString(): String {
        var parts: MutableList<String> = ArrayList()
        var output = when {
            meter >= 1000000000 -> "${String.format("%3.3E", meter/1000)} km = "
            meter >= 1000 -> "${(meter/1000).format(2)} km = "
            meter < 0.01 -> "${(meter*1000).format(2)} mm = "
            meter < 1 -> "${(meter*100).format(2)} cm = "
            else -> "${meter.format(2)} m = "
        }
        if (foot >= 5280) {
            when {
                mile >= 1000000 -> parts.add("${String.format("%3.3E", mile)} miles")
                else -> parts.add("${mile.format(2)} miles")
            }
        } else {
            if (foot > 0.01)
                parts.add("$foot feet")
            if (inch > 0.1)
                parts.add("${inch.format(2)} inches")
        }
        output += parts.joinToString()
        return output
    }
}

