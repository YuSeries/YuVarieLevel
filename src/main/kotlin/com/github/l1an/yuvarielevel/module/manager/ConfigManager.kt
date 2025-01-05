package com.github.l1an.yuvarielevel.module.manager

import org.serverct.parrot.parrotx.mechanism.Reloadable
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object ConfigManager {

    @Config("config.yml")
    @Reloadable
    lateinit var config: Configuration
        private set

}