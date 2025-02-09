package com.desmond_david.tgLastFmBotV2

import com.desmond_david.tgLastFmBotV2.bot.LastFmBot
import com.desmonddavid.lfm4j.Lfm4J
import com.desmonddavid.lfm4j.common.utils.ClientType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

private val logger = KotlinLogging.logger {}

fun main() {
    logger.info { "Starting TGLastFmBotV2..." }
    Lfm4J.init(BotProperties.LAST_FM_API_KEY, BotProperties.LAST_FM_API_SECRET, ClientType.DESKTOP)

    try {
        val botsApplication = TelegramBotsLongPollingApplication()
        val telegramClient = OkHttpTelegramClient(BotProperties.TELEGRAM_BOT_TOKEN)
        val lastFmBot = LastFmBot(telegramClient, BotProperties.TELEGRAM_BOT_NAME)
        lastFmBot.onRegister()
        botsApplication.registerBot(BotProperties.TELEGRAM_BOT_TOKEN, lastFmBot)
    } catch (e: TelegramApiException) {
        logger.error(e) { "An exception occurred while initializing the bot." }
    }
}
