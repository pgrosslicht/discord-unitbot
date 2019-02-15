package com.grosslicht.unitbot

import com.grosslicht.unitbot.commands.CommandDispatcher
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder

fun main(args: Array<String>) {
    val builder = JDABuilder(AccountType.BOT)
    val token = System.getenv("DISCORD_API_TOKEN")
    builder.setToken(token)
    builder.addEventListener(CommandDispatcher())
    val jda = builder.build().awaitReady()
}
