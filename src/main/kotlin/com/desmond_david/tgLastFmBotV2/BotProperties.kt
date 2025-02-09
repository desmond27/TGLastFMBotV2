package com.desmond_david.tgLastFmBotV2

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.*

object BotProperties {
    private val logger = KotlinLogging.logger {}

    @JvmStatic
    val TELEGRAM_BOT_TOKEN : String
    @JvmStatic
    val TELEGRAM_BOT_NAME : String
    @JvmStatic
    val TELEGRAM_CREATOR_ID : Long
    val LAST_FM_API_KEY : String
    val LAST_FM_API_SECRET : String
    init {
        logger.info { "Loading properties..." }
        val inputStream = BotProperties::class.java.classLoader.getResource("bot.properties")?.openStream()
        val properties = Properties()
        properties.load(inputStream)

        TELEGRAM_BOT_TOKEN = properties.getProperty("TELEGRAM_BOT_TOKEN")
        TELEGRAM_BOT_NAME = properties.getProperty("TELEGRAM_BOT_NAME")
        TELEGRAM_CREATOR_ID = properties.getProperty("TELEGRAM_CREATOR_ID").toLong()
        LAST_FM_API_KEY = properties.getProperty("LAST_FM_API_KEY")
        LAST_FM_API_SECRET = properties.getProperty("LAST_FM_API_SECRET")
        logger.debug { "Loaded properties: $properties" }
        logger.info { "Loaded properties..." }
    }
}