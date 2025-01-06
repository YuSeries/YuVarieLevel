package com.github.l1an.yuvarielevel.module.kether

import com.github.l1an.yuvarielevel.core.level.LevelManager
import com.github.l1an.yuvarielevel.core.level.LevelManager.getLevel
import com.github.l1an.yuvarielevel.core.level.LevelManager.giveExperience
import com.github.l1an.yuvarielevel.core.level.LevelManager.setExperience
import taboolib.library.kether.ArgTypes
import taboolib.module.kether.KetherParser
import taboolib.module.kether.actionNow
import taboolib.module.kether.scriptParser
import taboolib.module.kether.switch

@Suppress("unused", "DuplicatedCode")
object ActionExp {

    /**
     * exp get <id>      - 获取对应经验
     * exp getMax <id>   - 获取对应最大经验
     * exp give <id>     - 给予对应经验
     * exp set <id>      - 设置对应经验
     */
    @KetherParser(["exp"], namespace = NAMESPACE, shared = true)
    fun parser() = scriptParser {
        it.switch {
            case("get") {
                val id = it.next(ArgTypes.ACTION)
                actionNow {
                    val player = getBukkitPlayer()
                    val option = LevelManager.getLevelOption(id.toString())
                    if (option != null) {
                        player.getLevel(option).experience
                    } else {
                        error("Unknown level id: $id")
                    }
                }
            }
            case("getMax") {
                val id = it.next(ArgTypes.ACTION)
                actionNow {
                    val player = getBukkitPlayer()
                    val option = LevelManager.getLevelOption(id.toString())
                    if (option != null) {
                        option.algorithm.getExp(player.getLevel(option).level).getNow(0)
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
                        player.giveExperience(option, value)
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
                        player.setExperience(option, value)
                    } else {
                        error("Unknown level id: $id")
                    }
                }
            }
        }
    }

}