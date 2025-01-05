package com.github.l1an.yuvarielevel.module.compat.papi.impl

import com.github.l1an.yuvarielevel.api.event.PlaceholderHookEvent
import org.serverct.parrot.parrotx.module.level.LevelManager.getLevel
import org.serverct.parrot.parrotx.module.level.LevelManager.getLevelOption
import org.serverct.parrot.parrotx.util.get
import taboolib.common.platform.event.SubscribeEvent
import taboolib.expansion.getDataContainer

@SubscribeEvent
private fun onRequestPlaceholder(e: PlaceholderHookEvent) {
    when (e.identifier) {
        // 等级
        "level" -> {
            val option = getLevelOption(e.parameter)
            if (option != null) {
                e.result = e.player.getLevel(option).level
            } else {
                e.result = "LEVEL_OPTION_NOT_FOUND"
            }
        }
        // 经验
        "exp" -> {
            val option = getLevelOption(e.parameter)
            if (option != null) {
                e.result = e.player.getLevel(option).experience
            } else {
                e.result = "LEVEL_OPTION_NOT_FOUND"
            }
        }
        // 最大经验
        "max-exp" -> {
            val option = getLevelOption(e.parameter)
            if (option != null) {
                e.result = option.algorithm.getExp(e.player.getLevel(option).level).getNow(0)
            } else {
                e.result = "LEVEL_OPTION_NOT_FOUND"
            }
        }
        // 数据
        "data" -> {
            val container = e.player.getDataContainer()
            e.result = container[e.parameter, "null"]
        }
    }
}