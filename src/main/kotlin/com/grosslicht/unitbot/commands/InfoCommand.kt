package com.grosslicht.unitbot.commands

import mu.KLogging
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.io.FileInputStream
import java.io.InputStream
import java.lang.management.ManagementFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by patrickgrosslicht on 12/11/16.
 */
class InfoCommand: ListenerAdapter() {
    companion object: KLogging()

    fun getVersion(): String {
        val prop = Properties()
        var input: InputStream?
        input = FileInputStream("build.properties")
        prop.load(input)
        return "I am UnitBot v${prop.getProperty("version")}."
    }

    override fun onMessageReceived(event: MessageReceivedEvent?) {
        if (event == null || event.message.mentionsEveryone() || event.message.author.isBot)
            return
        if (event.message.isMentioned(event.message.jda.selfUser)) {
            logger.debug { "Handling message #${event.message.id}: ${event.message.content} from ${event.message.author}" }
            if (event.message.strippedContent.contains("uptime")) {
                val millis = ManagementFactory.getRuntimeMXBean().uptime
                event.message.channel.sendMessage("I have been up for ${String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))}.").queue()
            } else if (event.message.strippedContent.contains("version")) {
                event.message.channel.sendMessage(getVersion()).queue()
            }
        }
    }
}
