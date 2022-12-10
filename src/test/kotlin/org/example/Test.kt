package org.example

import Generator.Companion.generateRandom
import ILP.ILPSolver
import Logger.Logger
import org.junit.jupiter.api.Test
import java.io.File

class Test {

    @Test
    fun testILPSolver() {
        ILPSolver().solve(2, mutableMapOf(0 to 40, 2 to 50, 3 to 9, 7 to 26))
    }

    @Test
    fun testJson() {
        val game = FileHandler.fromJSON(File("data/test1.json").readText())
        ILPSolver().solve(game!!)

        println( FileHandler.getJSON(game))
    }

    @Test
    fun testRandomGenerator() {
        Logger.info("Generating random file and solving it ...")
        ILPSolver().solve(generateRandom(2))
    }

    @Test
    fun testFileReader() {
        val game = FileHandler.readBasicFile("data/basicFile.txt")
        ILPSolver().solve(game)
    }
}