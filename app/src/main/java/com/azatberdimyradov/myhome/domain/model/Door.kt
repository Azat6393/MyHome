package com.azatberdimyradov.myhome.domain.model

data class Door(
    val favorites: Boolean,
    val id: Int,
    val name: String,
    val room: String,
    val snapshot: String
)