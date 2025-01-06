package com.github.l1an.yuvarielevel.core.level

import com.github.l1an.artisan.feature.sendInfo
import com.github.l1an.artisan.lang.LanguageType
import com.github.l1an.artisan.lang.sendLang
import com.github.l1an.yuvarielevel.api.event.LevelChangeEvent
import com.github.l1an.artisan.utils.get
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.expansion.getDataContainer
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import java.util.concurrent.CompletableFuture

/**
 * Chemdah
 * ink.ptms.chemdah.module.level.LevelSystem
 *
 * @author sky
 * @since 2021/3/8 11:13 下午
 */
object LevelManager {

    @Config("level.yml")
    lateinit var conf: Configuration
        private set

    val level = HashMap<String, LevelOption>()

    fun getLevelOption(name: String): LevelOption? {
        return level[name]
    }

    /**
     * 设置玩家等级数据
     *
     * @param option 经验配置
     * @param playerLevel 玩家经验数据
     */
    fun Player.setLevel(option: LevelOption, playerLevel: PlayerLevel) {
        setLevel(option, playerLevel.level, playerLevel.experience)
    }

    /**
     * 设置玩家等级数据
     *
     * @param option 经验配置
     * @param level 等级
     * @param experience 经验
     */
    fun Player.setLevel(option: LevelOption, level: Int, experience: Int) {
        val container = getDataContainer()
        val p = getLevel(option)
        val event = LevelChangeEvent(this, option, p.level, p.experience, level, experience)
        if (event.call()) {
            container["level.${option.id}.level"] = event.newLevel
            container["level.${option.id}.experience"] = event.newExperience
        }
    }

    /**
     * 获取玩家等级数据
     *
     * @param option 经验配置
     * @return [PlayerLevel]
     */
    fun Player.getLevel(option: LevelOption): PlayerLevel {
        val container = getDataContainer()
        return PlayerLevel(
            container["level.${option.id}.level", 0].toInt(),
            container["level.${option.id}.experience", 0].toInt()
        )
    }

    /**
     * 设置玩家等级
     *
     * @param option 经验配置
     * @param value 经验值
     * @return [CompletableFuture<PlayerLevel>]
     */
    fun Player.setLevel(option: LevelOption, value: Int): CompletableFuture<PlayerLevel> {
        return CompletableFuture<PlayerLevel>().also { future ->
            val level = option.toLevel(getLevel(option))
            level.setLevel(value).thenAccept {
                val playerLevel = level.toPlayerLevel()
                setLevel(option, playerLevel)
                future.complete(playerLevel)
            }
        }
    }

    /**
     * 给予玩家等级
     *
     * @param option 经验配置
     * @param value 经验值
     * @return [CompletableFuture<PlayerLevel>]
     */
    fun Player.giveLevel(option: LevelOption, value: Int): CompletableFuture<PlayerLevel> {
        return CompletableFuture<PlayerLevel>().also { future ->
            val level = option.toLevel(getLevel(option))
            level.addLevel(value).thenAccept {
                val playerLevel = level.toPlayerLevel()
                setLevel(option, playerLevel)
                future.complete(playerLevel)
            }
        }
    }

    /**
     * 设置玩家经验值
     *
     * @param option 经验配置
     * @param value 经验值
     * @return [CompletableFuture<PlayerLevel>]
     */
    fun Player.setExperience(option: LevelOption, value: Int): CompletableFuture<PlayerLevel> {
        return CompletableFuture<PlayerLevel>().also { future ->
            val level = option.toLevel(getLevel(option))
            level.setExperience(value).thenAccept {
                val playerLevel = level.toPlayerLevel()
                setLevel(option, playerLevel)
                future.complete(playerLevel)
            }
        }
    }

    /**
     * 给予玩家经验值
     *
     * @param option 经验配置
     * @param value 经验值
     * @return [CompletableFuture<PlayerLevel>]
     */
    fun Player.giveExperience(option: LevelOption, value: Int): CompletableFuture<PlayerLevel> {
        return CompletableFuture<PlayerLevel>().also { future ->
            val level = option.toLevel(getLevel(option))
            level.addExperience(value).thenAccept {
                val playerLevel = level.toPlayerLevel()
                setLevel(option, playerLevel)
                future.complete(playerLevel)
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private fun onLevelChange(e: LevelChangeEvent) {
        val player = e.player

        // 给予等级奖励
        if (e.newLevel > e.oldLevel) {
            ((e.oldLevel + 1)..e.newLevel).forEach { level ->
                e.option.getReward(level)?.eval(player, level)
            }
        }

        // 发送通知
        if (e.oldLevel != e.newLevel) player.sendLang(
            "level-level-change",
            e.option.id to "option", e.newLevel to "level", e.newExperience to "exp",
            type = LanguageType.Info
        ) else if (e.oldExperience != e.newExperience) player.sendLang(
            "level-exp-change",
            e.option.id to "option", e.newLevel to "level", e.newExperience to "exp",
            type = LanguageType.Info
        ) else return
    }

    @Awake(LifeCycle.ACTIVE)
    fun load() {
        reload(null)
    }

    fun reload(sender: CommandSender?) {
        level.clear()
        conf.reload()
        conf.getKeys(false).forEach { node ->
            val section = conf.getConfigurationSection(node)!!
            val algorithm = AlgorithmKether(section)
            level[node] = LevelOption(algorithm, section.getInt("min"), section)
        }
        if (sender == null) {
            sendInfo("&a已成功加载 &e${level.size} &a个等级配置, 配置共 &e${conf.getKeys(false).size} &a个.")
        } else {
            sender.sendLang("level-load-success", level.size to "amount", conf.getKeys(false).size to "total",
                type = LanguageType.Done
            )
        }
    }

}