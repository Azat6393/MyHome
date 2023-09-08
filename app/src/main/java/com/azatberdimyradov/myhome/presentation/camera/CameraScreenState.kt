package com.azatberdimyradov.myhome.presentation.camera

import com.azatberdimyradov.myhome.domain.model.Camera

data class CameraScreenState(
    val cameras: List<Camera> = emptyList(),
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
