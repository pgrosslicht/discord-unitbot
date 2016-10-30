package com.grosslicht.unitbot.utils

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)