package io.github.fierg.model

import io.github.fierg.extensions.toDigits
import io.github.fierg.model.dto.GameDTO


data class EncryptedGameInstance(val nrOfSymbols: Int, val symbols: Map<Int, Array<Symbol?>>) {

    companion object {
        fun fromGameInstance(game: GameInstance): EncryptedGameInstance {
            val map = mutableMapOf<Int, Array<Symbol?>>()
            val shuffledSymbols = Symbol.values()
            shuffledSymbols.shuffle()
            (0..8).forEach { index ->
                map[index] = when (index / 3) {
                    0 -> game.row1[index % 3].toDigits().map { shuffledSymbols[it] }.toTypedArray()
                    1 -> game.row2[index % 3].toDigits().map { shuffledSymbols[it] }.toTypedArray()
                    2 -> game.row3[index % 3].toDigits().map { shuffledSymbols[it] }.toTypedArray()
                    else -> {
                        throw IllegalStateException()
                    }
                }
            }
            return EncryptedGameInstance(game.nrOfSymbols, map)
        }
    }

    fun toGameDTO(serverID: String, gameID: Int): GameDTO {
        val row1 = arrayOf(
            this.symbols[0]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it },
            this.symbols[1]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it },
            this.symbols[2]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it })
        val row2 = arrayOf(this.symbols[3]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it },
            this.symbols[4]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it },
            this.symbols[5]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it })
        val row3 = arrayOf(this.symbols[6]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it },
            this.symbols[7]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it },
            this.symbols[8]!!.map { it.toString().lowercase() }.joinToString(separator = "") { it })
        return GameDTO(serverID, gameID, row1, row2, row3)
    }
}