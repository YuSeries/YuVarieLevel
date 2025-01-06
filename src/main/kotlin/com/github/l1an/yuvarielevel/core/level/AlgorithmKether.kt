package com.github.l1an.yuvarielevel.core.level

import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.kether.KetherShell.eval
import taboolib.module.kether.ScriptOptions
import taboolib.module.kether.printKetherErrorMessage
import java.util.concurrent.CompletableFuture

/**
 * Chemdah
 * ink.ptms.chemdah.module.level.Level
 *
 * @author sky
 */
class AlgorithmKether(val section: ConfigurationSection) : Algorithm() {

    override val maxLevel: Int
        get() = section.getInt("max")

    override fun getExp(level: Int): CompletableFuture<Int> {
        return try {
            eval(section.getString("experience").toString(), ScriptOptions.builder().namespace(emptyList()).context {
                rootFrame().variables().set("level", level)
            }.build()).thenApply {
                Coerce.toInteger(it)
            }
        } catch (ex: Exception) {
            ex.printKetherErrorMessage()
            CompletableFuture.completedFuture(0)
        }
    }
}
