package org.fierg.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GameDTO(
    @JsonProperty("server_id") val serverID: String? = "",
    @JsonProperty("raetsel_id") val gameID: Int? = 0,
    @JsonProperty("row1") val row1: Array<String>? = arrayOf(),
    @JsonProperty("row2") val row2: Array<String>? = arrayOf(),
    @JsonProperty("row3") val row3: Array<String>? = arrayOf()
)