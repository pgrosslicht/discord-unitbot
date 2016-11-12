package com.grosslicht.unitbot.commands

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonParser
import mu.KLogging
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

class UnitConverter: ListenerAdapter() {
    companion object: KLogging()
    init {
        FuelManager.instance.basePath = System.getenv("MATHJS_HOST") ?: "http://${System.getenv("MATHJS_API_SERVICE_HOST")}:${System.getenv("MATHJS_API_SERVICE_PORT")}"
    }
    val parse = JsonParser()

    fun execute(msg: MessageReceivedEvent) {
        val filtered = msg.message.content.replace("@UnitBot", "").trim()
        "/eval".httpPost().body(jsonObject("expr" to filtered).toString()).header("Content-Type" to "application/json").responseString { request, response, result ->
            val (data, error) = result
            if (error == null) {
                var response = parse.parse(data.toString())
                msg.channel.sendMessage(response["result"].string).queue()
            } else {
                logger.error { error }
            }
        }
    }


    override fun onMessageReceived(event: MessageReceivedEvent?) {
        if (event == null || event.message.mentionsEveryone() || event.message.author.isBot)
            return
        if (event.message.isMentioned(event.message.jda.selfUser)) {
            logger.debug { "Handling message #${event.message.id}: ${event.message.content} from ${event.message.author}" }
            execute(event)
        }
    }
}
