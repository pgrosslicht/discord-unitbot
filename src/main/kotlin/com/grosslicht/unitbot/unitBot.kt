package com.grosslicht.unitbot

import com.grosslicht.unitbot.commands.UnitConverter
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder

/**
 * Created by patrickgrosslicht on 13/10/16.
 */

fun main(args: Array<String>) {
    val builder = JDABuilder(AccountType.BOT)
    //val token = System.getenv("DISCORD_API_TOKEN")
    val token = "MjM2NTYxODE1MjcyODE2NjQx.Cvuixw.UDQgfgR4exhRfB06pOGe81gqWUg"
    builder.setToken(token)
    builder.addListener(UnitConverter())
    val jda = builder.buildBlocking()
}
