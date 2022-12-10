package Model

import Extensions.length

data class GameInstance(val numbers: Array<Array<Int?>>, val nrOfSymbols: Int = numbers.maxOf { line -> line.maxOf { it.length() } })


