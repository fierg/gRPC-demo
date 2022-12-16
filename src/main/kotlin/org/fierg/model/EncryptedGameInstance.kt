package org.fierg.model

data class EncryptedGameInstance(val nrOfSymbols: Int, val symbols: Map<Int,Array<Symbol?>>)