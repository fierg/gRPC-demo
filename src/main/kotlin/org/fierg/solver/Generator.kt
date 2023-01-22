package org.fierg.solver

import org.fierg.extensions.length
import org.fierg.model.GameInstance
import kotlin.math.pow
import kotlin.random.Random

class Generator {
    companion object {
        fun generateRandom(nrOfDigits: Int): GameInstance {
            val r = Random(System.nanoTime())
            var invalid = false
            val game = Array(3) { Array(3) { 0 } }

            do {
                for (i in 0..2) {
                    val t = r.nextInt(0, 10.0.pow(nrOfDigits).toInt())
                    game[i][0] = t
                }
                for (i in 0..2) {
                    val t = r.nextInt(0, 10.0.pow(nrOfDigits).toInt())
                    game[i][1] = t
                }
                for (i in 0..2) {
                    game[i][2] = game[i][0] + game[i][1]
                    if (game[i][2].length() > nrOfDigits)
                        invalid = true
                }
            } while (invalid)

            return GameInstance(nrOfDigits, game[0], game[1], game[2])
        }
    }
}