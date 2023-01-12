package org.fierg

import org.fierg.solver.BruteForceSolver
import org.fierg.solver.FileHandler
import org.junit.jupiter.api.Test
import java.io.File

class Test {

    @Test
    fun testEncryptedSolve(){
        val game = FileHandler.readEncryptedFile(File("data/encryptedFile.txt").readText())
        BruteForceSolver().solve(game)
    }

    @Test
    fun testEncryptedSolve2(){
        val game = FileHandler.readEncryptedFile(File("data/encryptedFile2.txt").readText())
        BruteForceSolver().solve(game)
    }
}