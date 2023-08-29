package com.azatberdimyradov.myhome.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoorResponse(
    @SerialName("data")
    val _data: List<DoorDataDto>? = null,
    val success: Boolean? = null
)