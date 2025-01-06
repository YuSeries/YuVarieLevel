package com.github.l1an.yuvarielevel.module.command.subcommand

import com.github.l1an.artisan.lang.LanguageType
import com.github.l1an.artisan.lang.sendLang
import com.github.l1an.artisan.utils.toPlayer
import org.bukkit.command.CommandSender
import com.github.l1an.yuvarielevel.core.level.LevelManager
import com.github.l1an.yuvarielevel.core.level.LevelManager.getLevel
import com.github.l1an.yuvarielevel.core.level.LevelManager.getLevelOption
import com.github.l1an.yuvarielevel.core.level.LevelManager.giveExperience
import com.github.l1an.yuvarielevel.core.level.LevelManager.giveLevel
import com.github.l1an.yuvarielevel.core.level.LevelManager.setExperience
import com.github.l1an.yuvarielevel.core.level.LevelManager.setLevel
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggest
import taboolib.common5.Coerce

/**
 * level addlevel <player> <levelOption> <value> - 增加等级
 * level setlevel <player> <levelOption> <value> - 设置等级
 * level addexp <player> <levelOption> <value>   - 增加经验
 * level setexp <player> <levelOption> <value>   - 设置经验
 * level info <player>                           - 查看所有等级信息
 * level info <player> <levelOption>             - 查看指定等级信息
 */
val CommandLevel = subCommand {

    literal("addlevel") {
        player {
            dynamic("level") {
                suggest { LevelManager.level.keys.toList() }
                dynamic("value") {
                    exec<CommandSender> {
                        val option = getLevelOption(ctx["level"])!!
                        val player = ctx.toPlayer()
                        player.giveLevel(option, Coerce.toInteger(ctx["value"]))
                    }
                }
            }
        }
    }

    literal("setlevel") {
        player {
            dynamic("level") {
                suggest { LevelManager.level.keys.toList() }
                dynamic("value") {
                    exec<CommandSender> {
                        val option = getLevelOption(ctx["level"])!!
                        val player = ctx.toPlayer()
                        player.setLevel(option, Coerce.toInteger(ctx["value"]))
                    }
                }
            }
        }
    }

    literal("addexp") {
        player {
            dynamic("level") {
                suggest { LevelManager.level.keys.toList() }
                dynamic("value") {
                    exec<CommandSender> {
                        val option = getLevelOption(ctx["level"])!!
                        val player = ctx.toPlayer()
                        player.giveExperience(option, Coerce.toInteger(ctx["value"]))
                    }
                }
            }
        }
    }

    literal("setexp") {
        player {
            dynamic("level") {
                suggest { LevelManager.level.keys.toList() }
                dynamic("value") {
                    exec<CommandSender> {
                        val option = getLevelOption(ctx["level"])!!
                        val player = ctx.toPlayer()
                        player.setExperience(option, Coerce.toInteger(ctx["value"]))
                    }
                }
            }
        }
    }

    literal("info") {
        player {
            dynamic("option") {
                suggest { LevelManager.level.keys.toList() }
                exec<CommandSender> {
                    val player = ctx.toPlayer()
                    val option = getLevelOption(ctx["option"])
                    if (option != null) {
                        sender.sendLang("level-info-header", player.name to "player", type = LanguageType.Info)
                        sender.sendLang("level-info-body",
                            option.id to "option",
                            player.getLevel(option).level to "level",
                            option.algorithm.maxLevel to "max-level",
                            player.getLevel(option).experience to "exp",
                            option.algorithm.getExp(player.getLevel(option).level).getNow(0) to "max-exp",
                        )
                    }
                }
            }
            exec<CommandSender> {
                val player = ctx.toPlayer()
                // TODO: 顯示玩家所有等級信息
            }
        }
    }

}