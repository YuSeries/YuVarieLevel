package com.github.l1an.yuvarielevel.module.compat.papi

import com.github.l1an.yuvarielevel.module.kether.NAMESPACE
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions
import taboolib.module.kether.printKetherErrorMessage
import taboolib.platform.compat.PlaceholderExpansion

object PlaceholderForKether : PlaceholderExpansion {

    override val identifier: String
        get() = "yuvarielevel"

    override fun onPlaceholderRequest(player: Player?, args: String): String {
        player ?: return "Not found player"
        return try {
            KetherShell.eval(args, ScriptOptions.new {
                sender(adaptPlayer(player))
                namespace(listOf(NAMESPACE))
            }).getNow(null).toString()
        } catch (ex: Throwable) {
            ex.printKetherErrorMessage()
            return "<ERROR>"
        }
    }

}