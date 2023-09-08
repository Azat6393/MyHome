package com.azatberdimyradov.myhome.presentation.door

import com.azatberdimyradov.myhome.domain.model.Door

data class DoorScreenState(
    val doors: List<Door> = emptyList(),
    val errorMessage: String = ""
)
