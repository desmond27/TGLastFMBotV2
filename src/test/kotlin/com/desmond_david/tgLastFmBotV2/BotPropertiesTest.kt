package com.desmond_david.tgLastFmBotV2

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BotPropertiesTest {
    @Test
    fun testLoadingBotProperties() {
        assertEquals("test-bot-token-019928734", BotProperties.TELEGRAM_BOT_TOKEN)
        assertEquals("BlastFM_bot", BotProperties.TELEGRAM_BOT_NAME)
        assertEquals(43102847, BotProperties.TELEGRAM_CREATOR_ID)
        assertEquals("test-last-fm-api-key-647913234", BotProperties.LAST_FM_API_KEY)
        assertEquals("test-last-fm-secret-1203847", BotProperties.LAST_FM_API_SECRET)
    }
}
