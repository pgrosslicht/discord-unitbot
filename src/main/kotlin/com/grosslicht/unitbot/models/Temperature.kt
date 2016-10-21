package com.grosslicht.unitbot.models

import com.grosslicht.unitbot.utils.format

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

data class Temperature(val celsius: Double): Convertible {
    val fahrenheit: Double
    val kelvin: Double
    init {
        fahrenheit = celsiusToFahrenheit(celsius)
        kelvin = celsiusToKelvin(celsius)
    }
    override fun toString(): String {
        return "${celsius.format(2)} °C = ${fahrenheit.format(2)} °F = ${kelvin.format(2)} K"
    }

    fun celsiusToFahrenheit(c: Double): Double = (9.0/5.0) * c + 32
    fun celsiusToKelvin(c: Double): Double = (c + 273.15)
}
