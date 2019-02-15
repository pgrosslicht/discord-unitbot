package com.grosslicht.unitbot.commands

import mu.KLogging
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.lang.management.ManagementFactory
import java.util.Properties
import java.util.concurrent.TimeUnit

class CommandDispatcher : ListenerAdapter() {
    companion object : KLogging()

    private val unitConverter: UnitConverter = UnitConverter()
    private val ownerId = "135726717363945472"

    private val version: String by lazy {
        val prop = Properties()
        val input = javaClass.classLoader.getResourceAsStream("build.properties")
        prop.load(input)
        prop.getProperty("version")
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.mentionsEveryone() || event.message.author.isBot || !event.message.isMentioned(event.message.jda.selfUser))
            return
        logger.debug { "Handling message #${event.message.id}: ${event.message.contentStripped} from ${event.message.author}" }
        when {
            event.message.contentStripped.contains("uptime") -> {
                val millis = ManagementFactory.getRuntimeMXBean().uptime
                event.message.channel.sendMessage(
                    "I have been up for ${String.format(
                        "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
                    )}."
                ).queue()
            }
            event.message.contentStripped.contains("version") -> event.message.channel.sendMessage("I am UnitBot v$version").queue()
            event.message.contentStripped.contains("status") && event.author.id == ownerId -> when {
                event.message.contentDisplay.contains("invisible") -> event.jda.presence.status =
                        OnlineStatus.INVISIBLE
                event.message.contentDisplay.contains("offline") -> event.jda.presence.status =
                        OnlineStatus.INVISIBLE
                event.message.contentDisplay.contains("dnd") -> event.jda.presence.status =
                        OnlineStatus.DO_NOT_DISTURB
                event.message.contentDisplay.contains("afk") -> event.jda.presence.status = OnlineStatus.IDLE
                else -> event.jda.presence.status = OnlineStatus.ONLINE
            }
            else -> unitConverter.execute(event)
        }
    }
}
