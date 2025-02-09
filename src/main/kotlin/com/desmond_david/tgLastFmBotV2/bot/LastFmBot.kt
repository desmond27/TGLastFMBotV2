package com.desmond_david.tgLastFmBotV2.bot

import com.desmond_david.tgLastFmBotV2.BotProperties
import com.desmonddavid.lfm4j.artist.response.Artist
import com.desmonddavid.lfm4j.common.response.Image
import com.desmonddavid.lfm4j.user.LfmUserService
import com.desmonddavid.lfm4j.user.response.topAlbums.Album
import io.github.oshai.kotlinlogging.KotlinLogging
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot
import org.telegram.telegrambots.abilitybots.api.objects.Ability
import org.telegram.telegrambots.abilitybots.api.objects.Locality
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext
import org.telegram.telegrambots.abilitybots.api.objects.Privacy
import org.telegram.telegrambots.meta.generics.TelegramClient
import java.util.function.Consumer

@Suppress("unused")
class LastFmBot(telegramClient: TelegramClient?, botUsername: String?) : AbilityBot(telegramClient, botUsername) {

    private val logger = KotlinLogging.logger {}

    override fun creatorId(): Long {
        return BotProperties.TELEGRAM_CREATOR_ID
    }

    fun getUserDetails(): Ability {
        return Ability.builder()
            .name("user")
            .info("Gets user's Last FM info.")
            .input(1)
            .locality(Locality.ALL)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext ->
                val lastFmUsername = ctx.arguments()[0]

                logger.info { "Getting last fm details for user: $lastFmUsername" }

                val user = LfmUserService.getInfo(lastFmUsername)
                val message = """
                    Info for user $lastFmUsername
                    Profile URL: ${user.url}
                    Playcount: ${user.playcount}
                    Artists: ${user.artistCount}
                    Albums: ${user.albumCount}
                    Tracks: ${user.trackCount}
                    Country: ${user.country}
                """.trimIndent()
                silent.send(message, ctx.chatId())
            }
            .build()
    }

    fun getUserNowPlaying(): Ability {
        return Ability.builder()
            .name("now")
            .info("Gets the user's currently playing or last played track.")
            .input(1)
            .locality(Locality.ALL)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext ->
                val lastFmUsername = ctx.arguments()[0]

                logger.info { "Getting last played details for user: $lastFmUsername" }

                val recentTracks = LfmUserService.getRecentTracks(lastFmUsername, 1, 1, null, null, null)
                val trackList = recentTracks.tracks
                val currentTrack = trackList[0]
                val imageUrl = currentTrack.images.stream()
                    .filter { i: Image -> i.size == "extralarge" }.findAny().get().text
                var isWas = "was"
                if (currentTrack.attributes != null && currentTrack.attributes["nowplaying"] == "true") {
                    isWas = "is"
                }

                val message = """
                    *$lastFmUsername* $isWas listening to
                    🎵 ${currentTrack.name}
                    👥 ${currentTrack.artist.text}
                    📀 ${currentTrack.album.text}
                    [​]($imageUrl)
                """.trimIndent()
                logger.debug { "Message to be sent: $message" }
                silent.sendMd(message, ctx.chatId())
            }
            .build()
    }

    fun getUserTopArtists(): Ability {
        return Ability.builder()
            .name("topartists")
            .info("Gets the user's top 10 artists")
            .input(1)
            .locality(Locality.ALL)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext ->
                val lastFmUsername = ctx.arguments()[0]

                logger.info { "Getting top artists for the user: $lastFmUsername" }

                val topArtists = LfmUserService.getTopArtists(lastFmUsername, 1, 10, null)
                val topArtistsList = topArtists.artists.toMutableList()
                topArtistsList.sortBy { a: Artist -> ((a.attributes["rank"] as String).toInt()) }
                val topArtistsMessage: MutableList<String> = ArrayList()

                topArtistsList.forEach { a ->
                    topArtistsMessage.add(
                        "${a.attributes["rank"].toString()}. *${a.name}* - (${a.playcount})"
                    )
                }

                val message = topArtistsMessage.joinToString("\n")
                silent.sendMd(message, ctx.chatId())
            }
            .build()
    }

    fun getUserTopAlbums(): Ability {
        return Ability.builder()
            .name("topalbums")
            .info("Gets the user's top 10 albums")
            .input(1)
            .locality(Locality.ALL)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext ->
                val lastFmUsername = ctx.arguments()[0]

                logger.info { "Getting top albums for the user: $lastFmUsername" }

                val topAlbums = LfmUserService.getTopAlbums(lastFmUsername, 1, 10, null)
                val topAlbumsList = topAlbums.albums.toMutableList()
                topAlbumsList.sortBy { a: Album -> (a.attributes["rank"] as String).toInt() }
                val topAlbumsMessage: MutableList<String> = ArrayList()

                topAlbumsList.forEach(Consumer { a: Album ->
                    topAlbumsMessage.add(
                        "${a.attributes["rank"].toString()}. *${a.name}* - ${a.artist.name} - (${a.playcount})"
                    )
                })

                val message = topAlbumsMessage.joinToString("\n")
                silent.sendMd(message, ctx.chatId())
            }
            .build()
    }
}