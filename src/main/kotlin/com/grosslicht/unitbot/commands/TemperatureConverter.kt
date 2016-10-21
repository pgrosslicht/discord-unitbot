package com.grosslicht.unitbot.commands

import com.grosslicht.unitbot.models.Temperature
import java.security.InvalidParameterException
import java.util.regex.Pattern

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

class TemperatureConverter: UnitConverter() {
    override val regex = Pattern.compile("(-?[0-9]*\\.?[0-9]*)[ °]*(K|C|F)")

    override fun convert(amount: Double, type: String): Temperature {
        val celsius: Double = when (type.toUpperCase()) {
            "K" -> kelvinToCelsius(amount)
            "F" -> fahrenheitToCelsius(amount)
            "C" -> amount
            else -> throw InvalidParameterException("not a valid unit")
        }
        return Temperature(celsius)
    }

    fun fahrenheitToCelsius(f: Double): Double = (f - 32) * (5.0/9.0)
    fun kelvinToCelsius(k: Double): Double = (k - 273.15)
}
