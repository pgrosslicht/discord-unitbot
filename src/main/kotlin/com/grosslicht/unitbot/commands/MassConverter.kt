package com.grosslicht.unitbot.commands

import com.grosslicht.unitbot.models.Mass
import java.security.InvalidParameterException
import java.util.regex.Pattern

/**
 * Created by patrickgrosslicht on 14/10/16.
 */
class MassConverter: UnitConverter() {
    override val regex = Pattern.compile("([0-9]*\\.?[0-9]*)[ ]*(lb|lbm|pound[s]?|ounce|oz|(?:kilo|)gram(?:me|)[s]?|[k]?g)", java.util.regex.Pattern.CASE_INSENSITIVE)

    override fun convert(amount: Double, type: String): Mass {
        val grams: Double = when (type.toLowerCase()) {
            "gram", "grams", "gramme", "grammes", "g" -> amount
            "kilogram", "kilograms", "kilogramme", "kilogrammes", "kg" -> amount*1000
            "lb", "lbm", "pound", "pounds" -> amount*453.59237
            "oz", "ounce" -> amount*28.35
            else -> throw InvalidParameterException("not a valid unit")
        }
        return Mass(grams)
    }
}