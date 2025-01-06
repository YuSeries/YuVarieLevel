package com.github.l1an.yuvarielevel.module.kether

import com.github.l1an.yuvarielevel.core.level.LevelManager
import com.github.l1an.yuvarielevel.core.level.LevelManager.getLevel
import com.github.l1an.yuvarielevel.core.level.LevelManager.giveLevel
import com.github.l1an.yuvarielevel.core.level.LevelManager.setLevel
import taboolib.library.kether.ArgTypes
import taboolib.module.kether.KetherParser
import taboolib.module.kether.actionNow
import taboolib.module.kether.scriptParser
import taboolib.module.kether.switch

@Suppress("unused", "DuplicatedCode")
object ActionLevel {

    /**
     * level get <id>          - 获取对应等级
     * level give <id> <value> - 给予对应等级
     * level set <id> <value>  - 设置对应等级
     */
    @KetherParser(["level"], namespace = NAMESPACE, shared = true)
    fun parser() = scriptParser {
        it.switch {
            case("get") {
                val id = it.next(ArgTypes.ACTION)
                actionNow {
                    val player = getBukkitPlayer()
                    val option = LevelManager.getLevelOption(id.toString())
                    if (option != null) {
                        player.getLevel(option).level
                    } else {
                        error("Unknown level id: $id")
                    }
                }
            }
            case("give") {
                val id = it.next(ArgTypes.ACTION)
                val value = it.next(ArgTypes.INT)
                actionNow {
                    val player = getBukkitPlayer()
                    val option = LevelManager.getLevelOption(id.toString())
                    if (option != null) {
                        player.giveLevel(option, value)
                    } else {
                        error("Unknown level id: $id")
                    }
                }
            }
            case("set") {
                val id = it.next(ArgTypes.ACTION)
                val value = it.next(ArgTypes.INT)
                actionNow {
                    val player = getBukkitPlayer()
                    val option = LevelManager.getLevelOption(id.toString())
                    if (option != null) {
                        player.setLevel(option, value)
                    } else {
                        error("Unknown level id: $id")
                    }
                }
            }
        }
    }

}