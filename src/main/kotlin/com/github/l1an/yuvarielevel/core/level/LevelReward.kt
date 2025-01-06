package com.github.l1an.yuvarielevel.core.level

import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions
import taboolib.module.kether.printKetherErrorMessage

/**
 * Chemdah
 * ink.ptms.chemdah.module.level.LevelReward
 *
 * @author sky
 * @since 2021/4/17 12:45 下午
 */
class LevelReward(val level: List<Int>, val script: List<String>) {

    fun eval(player: Player, level: Int) {
        try {
            KetherShell.eval(script, ScriptOptions.new {
                sender(adaptPlayer(player))
                set("level", level)
            })
        } catch (ex: Exception) {
            ex.printKetherErrorMessage()
        }
    }
}