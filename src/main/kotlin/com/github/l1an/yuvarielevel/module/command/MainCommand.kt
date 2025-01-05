package com.github.l1an.yuvarielevel.module.command

import com.github.l1an.yuvarielevel.module.command.subcommand.CommandLevel
import com.github.l1an.yuvarielevel.module.command.subcommand.CommandReload
import com.github.l1an.yuvarielevel.module.command.subcommand.CommandVar
import org.serverct.parrot.parrotx.feature.Debug.CommandDebug
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader(
    name = "yuvarielevel",
    aliases = ["yvl"],
    permission = "yuvarielevel.command.use",
    permissionDefault = PermissionDefault.OP
)
object MainCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody(permission = "yuvarielevel.command.reload", permissionDefault = PermissionDefault.OP)
    val reload = CommandReload

    @CommandBody(permission = "yuvarielevel.command.level", permissionDefault = PermissionDefault.OP)
    val level = CommandLevel

    @CommandBody(permission = "yuvarielevel.command.var", permissionDefault = PermissionDefault.OP)
    val `var` = CommandVar

    @CommandBody(permission = "yuvarielevel.command.debug", permissionDefault = PermissionDefault.OP)
    val debug = CommandDebug

}