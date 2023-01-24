package io.github.fierg.solver

import com.google.gson.Gson
import io.github.fierg.logger.LogConsumer
import io.github.fierg.model.EncryptedGameInstance
import io.github.fierg.model.Symbol
import io.github.fierg.model.dto.GameDTO

class FileHandler {
    companion object {
        fun readEncryptedFile(s: String): EncryptedGameInstance {
            val encryptedRegex = Regex("([A-J]+|[a-j]+) \\+ ([A-J]+|[a-j]+) = ([A-J]+|[a-j]+)")
            LogConsumer.getImpl().info("Reading game from encrypted string $s")
            val map = mutableMapOf<Int, Array<Symbol?>>()
            val size = s.split("\n").map { line ->
                val uLine = line.lowercase()
                if (encryptedRegex.matches(uLine)) {
                    encryptedRegex.find(uLine)!!.groupValues.filterIndexed { i, _ -> i > 0 }.maxOf { it.length }
                } else throw IllegalArgumentException("Game instance malformed! in line: $s")
            }.maxOf { it }
            var index = 0
            s.split("\n").map { line ->
                val groups = encryptedRegex.find(line)!!.groupValues.filterIndexed { i, _ -> i > 0 }
                groups.forEach { group ->
                    val array = Array<Symbol?>(size) { null }
                    group.toCharArray().forEachIndexed { arrayIndex, c ->
                        array[arrayIndex] = Symbol.valueOf(c.toString())
                    }
                    map[index++] = array.reversedArray()
                }
            }
            return EncryptedGameInstance(size, map)
        }

        fun readEncryptedJSON(s: String): EncryptedGameInstance {
            val gameDTO = Gson().fromJson(s, GameDTO::class.java)
            val sb = StringBuilder()
            sb.append("${gameDTO.row1!![0]} + ${gameDTO.row1[1]} = ${gameDTO.row1[2]}\n")
            sb.append("${gameDTO.row2!![0]} + ${gameDTO.row2[1]} = ${gameDTO.row2[2]}\n")
            sb.append("${gameDTO.row3!![0]} + ${gameDTO.row3[1]} = ${gameDTO.row3[2]}\n")
            return readEncryptedFile(sb.toString())
        }

    }
}