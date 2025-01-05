package com.github.l1an.yuvarielevel.module.command.subcommand

import org.bukkit.command.CommandSender
import org.serverct.parrot.parrotx.lang.LanguageType
import org.serverct.parrot.parrotx.lang.sendLang
import org.serverct.parrot.parrotx.util.toPlayer
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggest
import taboolib.common.platform.function.submitAsync
import taboolib.expansion.DataContainer
import taboolib.expansion.Database
import taboolib.expansion.getDataContainer

/**
 * var set <player> <key> <value> - 设置玩家变量
 * var clear <player>             - 清除玩家所有变量
 * var remove <player> <key>      - 删除玩家对应变量
 * var info <player>              - 列出玩家所有变量
 * var add <player> <key> <value> - 增加玩家变量
 */
val CommandVar = subCommand {

    literal("info") {
        player {
            exec<CommandSender> {
                val player = ctx.toPlayer()
                val container = player.getDataContainer()
                if (container.getKeys().isEmpty()) {
                    sender.sendLang("var-info-empty", player.name to "player", type = LanguageType.Error)
                    return@exec
                } else {
                    sender.sendLang("var-info-header", player.name to "player", type = LanguageType.Info)
                    container.getKeys().forEach {
                        sender.sendLang(
                            "var-info",
                            it to "key", container[it]!! to "value",
                            type = LanguageType.Info
                        )
                    }
                }
            }
        }
    }

    literal("set") {
        player {
            dynamic("key") {
                suggest { ctx.toPlayer().getDataContainer().getKeys().toList() }
                dynamic("value") {
                    exec<CommandSender> {
                        val player = ctx.toPlayer()
                        val container = player.getDataContainer()
                        container[ctx["key"]] = ctx["value"]
                        sender.sendLang("var-set", ctx["key"] to "key", ctx["value"] to "value",type = LanguageType.Done)
                    }
                }
            }
        }
    }

    literal("clear") {
        player {
            exec<CommandSender> {
                val player = ctx.toPlayer()
                val container = player.getDataContainer()
                container.getKeys().forEach {
                    container.remove(it)
                }
                sender.sendLang("var-clear", player.name to "player", type = LanguageType.Done)
            }
        }
    }

    literal("remove") {
        player {
            dynamic("key") {
                suggest { ctx.toPlayer().getDataContainer().getKeys().toList() }
                exec<CommandSender> {
                    val player = ctx.toPlayer()
                    val container = player.getDataContainer()
                    container.remove(ctx["key"])
                    sender.sendLang("var-remove", ctx["key"] to "key", type = LanguageType.Done)
                }
            }
        }
    }

    literal("add") {
        player {
            dynamic("key") {
                dynamic("value") {
                    exec<CommandSender> {
                        val player = ctx.toPlayer()
                        val container = player.getDataContainer()
                        val key = ctx["key"]
                        val value = ctx["value"]
                        if (container[key] != null) {
                            sender.sendLang("var-add-exist", key to "key", type = LanguageType.Error)
                            return@exec
                        }
                        container[key] = value
                        sender.sendLang("var-add", key to "key", value to "value", type = LanguageType.Done)
                    }
                }
            }
        }
    }

}

/**
 * 获取 container 中的 keys 除开等级信息后的 keys
 */
private fun DataContainer.getKeys() : Set<String> {
    return keys().filterNot { it.startsWith("level") }.toSet()
}

fun DataContainer.remove(key: String) {
    source.remove(key)
    saveRemove(key)
}

fun DataContainer.saveRemove(key: String) {
    submitAsync { database.remove(user, key) }
}

fun Database.remove(user: String, name: String) {
    if (get(user, name) == null) return
    else {
        type.tableVar().delete(dataSource) {
            where("user" eq user and ("key" eq name))
        }
    }
}