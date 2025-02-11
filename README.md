# TGLastFMBotV2
A simple Telegram bot that fetches information from Last FM. Re-written in Kotlin.

This project uses the [TelegramBots](https://github.com/rubenlagus/TelegramBots) library and my own Last FM wrapper library [lfm4j](https://github.com/desmond27/lfm4j).

See this bot in action: https://t.me/BlastFM_bot.

It does not need to be started. Just add to a chat and pass the commands given in the `Bot comamnds` section below.

Building
--------

The lfm4j library mentioned above is NOT in the Maven repository and therefore you will have to clone it and run `mvn install` to get this project to find it.

Also, before building, edit the [src/main/resources/bot.properties](https://github.com/desmond27/TGLastFMBotV2/blob/master/src/main/resources/bot.properties) file and add the following details:

|Property name |Description |
|--- |--- |
|TELEGRAM_BOT_TOKEN| Get from [BotFather](https://telegram.im/BotFather) when creating your bot in Telegram. |
|TELEGRAM_BOT_NAME| Whatever you set in BotFather after creating your bot. |
|TELEGRAM_CREATOR_ID| Your Telegram id (not handle). You can get it from [userinfobot](https://t.me/userinfobot). |
|LAST_FM_API_KEY| Your Last FM API key. Get it after creating an API account [here](https://www.last.fm/api/account/create). |
|LAST_FM_API_SECRET| Get it from the same link as for API key above. |

Bot commands
------------

The bot understands the following commands:

| Command                          | Description                                                                   |
|----------------------------------|-------------------------------------------------------------------------------|
| /now \<Last FM username\>        | Returns the currently playing or last played track by the given last fm user. |
| /user \<Last FM username\>       | Returns the profile information of the user.                                  |
| /topartists \<Last FM username\> | Returns the top 10 artists of the user.                                       |
| /topalbums \<Last FM username\>  | Returns the top 10 albums of the user.                                        |
