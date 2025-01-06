package com.github.l1an.yuvarielevel.module.command.subcommand

import com.github.l1an.artisan.feature.Reloadables
import com.github.l1an.artisan.lang.LanguageType
import com.github.l1an.artisan.lang.sendLang
import org.bukkit.command.CommandSender
import com.github.l1an.yuvarielevel.core.level.LevelManager
import taboolib.common.platform.command.subCommandExec

val CommandReload = subCommandExec<CommandSender> {
    Reloadables.execute()
    LevelManager.reload(sender)
    sender.sendLang("command-reload", type = LanguageType.Done)
}