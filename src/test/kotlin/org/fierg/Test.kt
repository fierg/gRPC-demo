package org.fierg

import org.fierg.solver.Generator.Companion.generateRandom
import org.fierg.solver.ILPSolver
import org.fierg.logger.Logger
import org.fierg.solver.BruteForceSolver
import org.fierg.solver.FileHandler
import org.junit.jupiter.api.Test
import java.io.File

class Test {


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

    @Test
    fun testEncryptedFile(){
        val game = FileHandler.readEncryptedFile(File("data/encryptedFile.txt").readText())
        ILPSolver().solve(game)
    }

    @Test
    fun testEncryptedSolve(){
        val game = FileHandler.readEncryptedFile(File("data/encryptedFile.txt").readText())
        BruteForceSolver().solve(game)
    }
}