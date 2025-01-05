package com.github.l1an.yuvarielevel.core.handler

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submitAsync
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

@Suppress("unused")
object PlayerHandler {

    @SubscribeEvent
    fun onJoin(e: PlayerJoinEvent) {
        submitAsync { e.player.setupDataContainer() }
    }

    @SubscribeEvent
    fun onQuit(e: PlayerQuitEvent) {
        e.player.releaseDataContainer()
    }

}