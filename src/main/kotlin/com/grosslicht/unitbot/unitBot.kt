package com.grosslicht.unitbot

import com.grosslicht.unitbot.commands.DistanceConverter
import com.grosslicht.unitbot.commands.MassConverter
import com.grosslicht.unitbot.commands.TemperatureConverter
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder

/**
 * Created by patrickgrosslicht on 13/10/16.
 */

fun main(args: Array<String>) {
    val builder = JDABuilder(AccountType.BOT)
    val token = System.getenv("DISCORD_API_TOKEN")
    builder.setToken(token)
    builder.addListener(DistanceConverter())
    builder.addListener(MassConverter())
    builder.addListener(TemperatureConverter())
    val jda = builder.buildBlocking()
}
