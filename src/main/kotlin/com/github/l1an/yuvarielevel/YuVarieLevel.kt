package com.github.l1an.yuvarielevel

import com.github.l1an.artisan.feature.sendInfo
import com.github.l1an.artisan.feature.setInfoPrefix
import com.github.l1an.artisan.feature.update.GithubUpdateChecker
import com.github.l1an.artisan.utils.sendEnableInfo
import com.github.l1an.yuvarielevel.module.manager.ConfigManager.config
import taboolib.common.io.newFile
import taboolib.common.platform.Platform
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.*
import taboolib.expansion.setupPlayerDatabase
import taboolib.module.metrics.Metrics

object YuVarieLevel : Plugin() {

    val messagePrefix = "&f[ &3YuVarieLevel &f]"

    override fun onEnable() {
        Metrics(23001, pluginVersion, Platform.BUKKIT)

        setInfoPrefix(messagePrefix)
        sendEnableInfo()

        try {
            if (config.getString("Database.Method")?.uppercase() == "MYSQL") {
                setupPlayerDatabase(
                    config.getConfigurationSection("Database.SQL")!!,
                    "${pluginId.lowercase()}_player_database"
                )
                sendInfo("&aNow using MySQL database.")
            } else {
                setupPlayerDatabase(newFile(getDataFolder(), "data.db"))
                sendInfo("&aNow using SQLite database.")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            sendInfo("&cCan't find database configuration, plugin is disabling...")
            disablePlugin()
            return
        }

        GithubUpdateChecker("L1-An", "YuVarieLevel").check()
    }
}