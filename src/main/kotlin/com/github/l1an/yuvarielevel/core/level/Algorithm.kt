package com.github.l1an.yuvarielevel.core.level

import java.util.concurrent.CompletableFuture

/**
 * Chemdah
 * ink.ptms.chemdah.module.level.Level
 *
 * @author sky
 */
abstract class Algorithm {

    abstract val maxLevel: Int

    abstract fun getExp(level: Int): CompletableFuture<Int>
}