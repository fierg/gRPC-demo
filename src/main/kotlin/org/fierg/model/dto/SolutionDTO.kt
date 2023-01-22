package org.fierg.model.dto

data class SolutionDTO(val server_id: String, val raetsel_id: Int, val row1: Array<Int>, val row2: Array<Int>, val row3: Array<Int>, val time: Double)
