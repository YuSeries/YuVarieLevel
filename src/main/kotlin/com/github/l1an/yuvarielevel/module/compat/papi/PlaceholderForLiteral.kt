package com.github.l1an.yuvarielevel.module.compat.papi

import com.github.l1an.yuvarielevel.api.event.PlaceholderHookEvent
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object PlaceholderForLiteral : PlaceholderExpansion {

    override val identifier: String
        get() = "yvl"

    /**
     * yvl_level_<id>         - 获取对应等级
     * yvl_exp_<id>           - 获取对应经验
     * yvl_max-exp_<id>        - 获取对应最大经验
     * yvl_data_<key>         - 获取对应数据
     */
    override fun onPlaceholderRequest(player: Player?, args: String): String {
        player ?: return "Not found player"
        val name = args.substringBefore('_').lowercase()
        val body = args.substringAfter('_')
        val event = PlaceholderHookEvent(player, name, body)
        event.call()
        return event.result?.toString() ?: "UNSUPPORTED"
    }

}