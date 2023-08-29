package com.azatberdimyradov.myhome.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CameraDto(
    val favorites: Boolean? = null,
    val id: Int? = null,
    val name: String? = null,
    val rec: Boolean? = null,
    val room: String? = null,
    val snapshot: String? = null
)