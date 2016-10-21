package com.grosslicht.unitbot.commands

import com.grosslicht.unitbot.models.Distance
import java.security.InvalidParameterException
import java.util.regex.Pattern

/**
 * Created by patrickgrosslicht on 14/10/16.
 */
class DistanceConverter: UnitConverter() {
    override val regex = Pattern.compile("([0-9]*\\.?[0-9]*)[ ]*(mile[s]?|mi|inch|in|foot|feet|ft|yard[s]?|yd|(?:milli|centi|kilo|)meter[s]?|[mkc]?m|ly|light-year[s]?|au|astronomical unit[s]?|parsec[s]?|pc)", java.util.regex.Pattern.CASE_INSENSITIVE)

    override fun convert(amount: Double, type: String): Distance {
        val meters: Double = when (type.toLowerCase()) {
            "meters", "meter", "m" -> amount
            "millimeters", "millimeter", "mm" -> amount/1000
            "kilometers", "kilometer", "km" -> amount*1000
            "miles", "mile", "mi" -> amount/0.00062137
            "inch", "in" -> amount*0.0254
            "centimeters", "centimeter", "cm" -> amount/100
            "feet", "foot", "ft" -> amount*0.3048
            "yards", "yard", "yd" -> amount*0.9144
            "light-year", "light-years", "ly" -> amount*9460730472580800
            "astronomical unit", "astronomical units", "au" -> amount*149597870700
            "parsec", "parsecs", "pc" -> amount*30856776376340068
            else -> throw InvalidParameterException("not a valid unit")
        }
        return Distance(meters)
    }
}