package com.grosslicht.unitbot.commands

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonParser
import mu.KLogging
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

class UnitConverter {
    companion object: KLogging()
    init {
        FuelManager.instance.basePath = System.getenv("MATHJS_HOST") ?: "http://${System.getenv("MATHJS_API_SERVICE_HOST")}:${System.getenv("MATHJS_API_SERVICE_PORT")}"
    }
    val parse = JsonParser()

    fun execute(msg: MessageReceivedEvent) {
        val filtered = msg.message.contentDisplay.replace("@UnitBot", "").trim()
        msg.channel.sendMessage("Processing…").queue({ msg ->
            "/eval".httpPost().body(jsonObject("expr" to filtered).toString()).header("Content-Type" to "application/json").responseString { request, response, result ->
                val (data, error) = result
                if (error == null) {
                    var response = parse.parse(data.toString())
                    msg.editMessage(response["result"].string).queue()
                } else {
                    msg.editMessage("Invalid expression.").queue()
                    logger.error { error }
                }
            }
        })
    }
}
