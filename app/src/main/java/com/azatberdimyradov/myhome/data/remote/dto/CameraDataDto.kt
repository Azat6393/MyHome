package com.azatberdimyradov.myhome.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CameraDataDto(
    @SerialName("cameras")
    val cameras: List<CameraDto>? = null,
    @SerialName("room")
    val room: List<String>? = null
)