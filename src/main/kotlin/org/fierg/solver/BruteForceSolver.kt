package org.fierg.solver

import org.fierg.extensions.Permutations
import org.fierg.logger.Logger
import org.fierg.model.EncryptedGameInstance
import org.fierg.model.Symbol
import kotlin.math.pow

class BruteForceSolver {

    fun solve(game: EncryptedGameInstance): String {
        val solution = solveEncrypted(game)
        return printSolution(game, solution)
    }

    private fun printSolution(game: EncryptedGameInstance, solution: Array<Int>): String {
        val sb = StringBuilder()
        for (position in 0..8) {
            game.symbols[position]!!.reversed().forEach { s ->
                if (s != null)
                    sb.append(solution[s.ordinal])
            }

            when (position % 3) {
                0 -> sb.append(" + ")
                1 -> sb.append(" = ")
                2 -> sb.append("\n")
            }
        }
        println(sb.toString())
        return sb.toString()
    }

    private fun solveEncrypted(game: EncryptedGameInstance): Array<Int> {
        val perms = Permutations(listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).toTypedArray())
        while (perms.hasNext()) {
            val currentSolution = perms.next()

            val value0 = getValueOfField(game, currentSolution, 0)
            val value1 = getValueOfField(game, currentSolution, 1)
            val value2 = getValueOfField(game, currentSolution, 2)

            if (value0 + value1 == value2) {
                val value3 = getValueOfField(game, currentSolution, 3)
                val value4 = getValueOfField(game, currentSolution, 4)
                val value5 = getValueOfField(game, currentSolution, 5)

                if (value3 + value4 == value5) {
                    val value6 = getValueOfField(game, currentSolution, 6)
                    val value7 = getValueOfField(game, currentSolution, 7)
                    val value8 = getValueOfField(game, currentSolution, 8)

                    if (value6 + value7 == value8) {
                        if (value0 + value3 == value6)
                            return currentSolution
                        else continue
                    }
                }
            }
        }

        Logger.error("Found no solution.")
        throw IllegalStateException("Found no solution.")
    }

    private fun getValueOfField(game: EncryptedGameInstance, currentSolution: Array<Int>, pos: Int) =
        game.symbols[pos]!!.foldIndexed(0) { index, acc, e -> if (e == null) acc else
            acc + currentSolution[Symbol.valueOf(e.toString()).ordinal] * 10.0.pow(index).toInt() }
}