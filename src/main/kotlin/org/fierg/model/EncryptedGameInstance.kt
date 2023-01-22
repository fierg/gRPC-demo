package org.fierg.model

import org.fierg.extensions.toDigits
import org.fierg.model.dto.GameDTO

data class EncryptedGameInstance(val nrOfSymbols: Int, val symbols: Map<Int, Array<Symbol?>>) {

    companion object{
        fun fromGameInstance(game:GameInstance): EncryptedGameInstance {
            val map = mutableMapOf<Int, Array<Symbol?>>()
            val shuffledSymbols = Symbol.values()
            shuffledSymbols.shuffle()
            (0..9).forEach {index ->
                map[index] = when (index/3){
                    0 -> game.row1[index%3].toDigits().map { shuffledSymbols[it] }.toTypedArray()
                    1 -> game.row2[index%3].toDigits().map { shuffledSymbols[it] }.toTypedArray()
                    2 -> game.row3[index%3].toDigits().map { shuffledSymbols[it] }.toTypedArray()
                    else -> {throw IllegalStateException()}
                }
            }
            return EncryptedGameInstance(game.nrOfSymbols, map)
        }
    }

    fun toGameDTO(serverID: String, gameID: Int): GameDTO {
        val row1 = arrayOf(this.symbols[0].toString(),this.symbols[1].toString(),this.symbols[2].toString())
        val row2 = arrayOf(this.symbols[3].toString(),this.symbols[4].toString(),this.symbols[5].toString())
        val row3 = arrayOf(this.symbols[6].toString(),this.symbols[7].toString(),this.symbols[8].toString())
        return GameDTO(serverID, gameID, row1, row2, row3)
    }
}