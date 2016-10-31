package com.grosslicht.unitbot.commands

import com.grosslicht.unitbot.models.Convertible
import mu.KLogging
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by patrickgrosslicht on 14/10/16.
 */

abstract class UnitConverter: ListenerAdapter() {
    companion object: KLogging()
    abstract val regex: Pattern

    fun match(x: String): List<Convertible> {
        val outputs: MutableList<Convertible> = ArrayList()
        var matcher: Matcher = regex.matcher(x)
        while (matcher.find()) {
            try {
                val temp: Convertible = convert(matcher.group(1).toDouble(), matcher.group(2))
                outputs.add(temp)
            } catch(e: NumberFormatException) {}
        }
        return outputs
    }

    abstract fun convert(amount: Double, type: String): Convertible

    override fun onMessageReceived(event: MessageReceivedEvent?) {
        if (event == null || event.message.mentionsEveryone() || event.message.author.isBot)
            return
        if (event.message.isMentioned(event.message.jda.selfInfo)) {
            logger.debug { "Handling message #${event.message.id}: ${event.message.content} from ${event.message.author}" }
            val list = match(event.message.content)
            if (list.isNotEmpty()) {
                event.message.channel.sendMessage(list.joinToString()).queue()
            }
        }
    }
}