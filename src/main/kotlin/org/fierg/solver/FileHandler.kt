package org.fierg.solver

import org.fierg.logger.LogConsumer
import org.fierg.model.EncryptedGameInstance
import org.fierg.model.Symbol

class FileHandler {
    companion object {
        fun readEncryptedFile(s: String): EncryptedGameInstance {
            val encryptedRegex = Regex("([A-J]+|[a-j]+) \\+ ([A-J]+|[a-j]+) = ([A-J]+|[a-j]+)")
            LogConsumer.getImpl().info("Reading game from encrypted string $s")
            val map = mutableMapOf<Int,Array<Symbol?>>()
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
                    val array = Array<Symbol?>(size) {null}
                    group.toCharArray().forEachIndexed { arrayIndex, c ->
                        array[arrayIndex] = Symbol.valueOf(c.toString())
                    }
                    map[index++] = array.reversedArray()
                }
            }
            return EncryptedGameInstance(size, map)
        }
    }
}