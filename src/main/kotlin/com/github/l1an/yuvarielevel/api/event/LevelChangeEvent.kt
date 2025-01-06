package com.github.l1an.yuvarielevel.api.event

import org.bukkit.entity.Player
import com.github.l1an.yuvarielevel.core.level.LevelOption
import taboolib.platform.type.BukkitProxyEvent

class LevelChangeEvent(
    val player: Player,
    val option: LevelOption,
    val oldLevel: Int,
    val oldExperience: Int,
    var newLevel: Int,
    var newExperience: Int,
) : BukkitProxyEvent()