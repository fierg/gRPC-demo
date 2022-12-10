package org.fierg.solver

import org.fierg.model.GameInstance
import kotlin.math.pow
import kotlin.random.Random

class Generator {
    companion object {
        fun generateRandom(nrOfPositions: Int): GameInstance {
            val r = Random(System.nanoTime())
            val game = Array(3) { Array<Int?>(3) { null } }

            for (i in 0..r.nextInt(2, 4)) {
                val t = Pair(r.nextInt(0, 9), r.nextInt(0, 10.0.pow(nrOfPositions).toInt()))
                if (game[t.first / 3][t.first % 3] == null) {
                    game[t.first / 3][t.first % 3] = t.second
                }
            }
            return GameInstance(game)
        }
    }
}