package com.azatberdimyradov.myhome.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CameraResponse(
    @SerialName("data")
    val _data: CameraDataDto? = null,
    val success: Boolean? = null
)