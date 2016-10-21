package com.grosslicht.unitbot

import com.grosslicht.unitbot.commands.DistanceConverter
import com.grosslicht.unitbot.commands.MassConverter
import com.grosslicht.unitbot.commands.TemperatureConverter
import sx.blah.discord.api.ClientBuilder

/**
 * Created by patrickgrosslicht on 13/10/16.
 */

fun main(args: Array<String>) {
    val builder = ClientBuilder()
    val token = System.getenv("DISCORD_API_TOKEN")
    builder.withToken(token)
    val client = builder.login()
    client.dispatcher.registerListener(DistanceConverter())
    client.dispatcher.registerListener(MassConverter())
    client.dispatcher.registerListener(TemperatureConverter())
}
