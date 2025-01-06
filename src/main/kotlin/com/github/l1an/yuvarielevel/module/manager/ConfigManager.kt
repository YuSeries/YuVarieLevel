package com.github.l1an.yuvarielevel.module.manager

import com.github.l1an.artisan.feature.Reloadable
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object ConfigManager {

    @Config("config.yml")
    @Reloadable
    lateinit var config: Configuration
        private set

}