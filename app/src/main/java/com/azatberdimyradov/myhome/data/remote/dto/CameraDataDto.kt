package com.azatberdimyradov.myhome.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CameraDataDto(
    val camera: List<CameraDto>? = null,
    val room: List<String>? = null
)