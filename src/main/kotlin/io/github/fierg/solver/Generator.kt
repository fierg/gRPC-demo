package io.github.fierg.solver

import io.github.fierg.extensions.length
import io.github.fierg.logger.impl.ConsoleLogger
import io.github.fierg.model.GameInstance
import kotlin.math.pow
import kotlin.random.Random

class Generator {
    companion object {
        fun generateRandom(nrOfDigits: Int): GameInstance {
            val r = Random(System.nanoTime())
            val game = Array(3) { Array(3) { 0 } }
            var tryNr = 0
            do {
                var invalid = false
                if (tryNr++ % 100 == 0)
                    ConsoleLogger.info("Generating puzzle ... (attempt nr $tryNr)")
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
                    if (game[i][2].length() > nrOfDigits){
                        invalid = true
                    }
                }
            } while (invalid)
            ConsoleLogger.info("Generated puzzle. (attempt nr $tryNr)")

            return GameInstance(nrOfDigits, game[0], game[1], game[2])
        }
    }
}

fun main(){
    Generator.generateRandom(3)
}