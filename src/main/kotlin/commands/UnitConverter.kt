package com.grosslicht.unitbot.commands

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpPost
import mu.KLogging
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

class UnitConverter {
    companion object : KLogging()

    init {
        FuelManager.instance.basePath = System.getenv("MATHJS_HOST")
                ?: "http://${System.getenv("MATHJS_API_SERVICE_HOST")}:${System.getenv("MATHJS_API_SERVICE_PORT")}"
    }

    data class Result(val result: String)

    fun execute(msg: MessageReceivedEvent) {
        val filtered = msg.message.contentDisplay.replace("@UnitBot", "").trim()
        msg.channel.sendMessage("Processing…").queue { msg ->
            "/eval".httpPost().jsonBody("expr" to filtered)
                .responseObject<Result> { _, _, result ->
                    result.fold({ success ->
                        msg.editMessage(success.result).queue()
                    }, { error ->
                        logger.error { error }
                        msg.editMessage("Invalid expression.").queue()
                    })
                }
        }
    }
}
