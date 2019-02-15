package com.grosslicht.unitbot.utils

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)