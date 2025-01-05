package com.github.l1an.yuvarielevel.module.command.subcommand

import org.bukkit.command.CommandSender
import org.serverct.parrot.parrotx.lang.LanguageType
import org.serverct.parrot.parrotx.lang.sendLang
import org.serverct.parrot.parrotx.mechanism.Reloadables
import org.serverct.parrot.parrotx.module.level.LevelManager
import taboolib.common.platform.command.subCommandExec

val CommandReload = subCommandExec<CommandSender> {
    Reloadables.execute()
    LevelManager.reload(sender)
    sender.sendLang("command-reload", type = LanguageType.Done)
}