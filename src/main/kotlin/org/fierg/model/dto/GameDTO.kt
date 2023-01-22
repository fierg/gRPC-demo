package org.fierg.model.dto

data class GameDTO(val server_id: String, val raetsel_id: Int, val row1: Array<String>, val row2: Array<String>, val row3: Array<String>)
