package com.azatberdimyradov.myhome.domain.model

data class Camera(
    val favorites: Boolean,
    val id: Int,
    val name: String,
    val rec: Boolean,
    val room: String,
    val snapshot: String
)