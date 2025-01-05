package com.github.l1an.yuvarielevel.module.kether

import org.bukkit.entity.Player
import taboolib.module.kether.ScriptFrame
import taboolib.module.kether.script

const val NAMESPACE = "yuvarielevel"

fun ScriptFrame.getBukkitPlayer(): Player {
    return script().sender?.castSafely<Player>() ?: error("No player selected.")
}