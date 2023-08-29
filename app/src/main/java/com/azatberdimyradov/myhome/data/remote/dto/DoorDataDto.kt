package com.azatberdimyradov.myhome.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DoorDataDto(
    val favorites: Boolean? = null,
    val id: Int? = null,
    val name: String? = null,
    val room: String? = null,
    val snapshot: String? = null
)