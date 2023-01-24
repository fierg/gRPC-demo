package io.github.fierg.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

open class SolutionDTO(
    @JsonProperty val server_id: String,
    @JsonProperty val raetsel_id: Int,
    @JsonProperty val row1: Array<Int>,
    @JsonProperty val row2: Array<Int>,
    @JsonProperty val row3: Array<Int>,
    @JsonProperty val time: Double
)
