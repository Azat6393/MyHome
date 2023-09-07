package com.azatberdimyradov.myhome.data

import com.azatberdimyradov.myhome.data.local.model.CameraModel
import com.azatberdimyradov.myhome.data.local.model.DoorModel
import com.azatberdimyradov.myhome.domain.model.Camera
import com.azatberdimyradov.myhome.domain.model.Door
import kotlin.random.Random


fun CameraModel.toCamera(): Camera {
    return Camera(
        id = id ?: Random.nextInt(),
        favorites = favorites ?: false,
        name = name ?: "",
        rec = rec ?: false,
        room = room ?: "",
        snapshot = snapshot ?: ""
    )
}

fun DoorModel.toDoor(): Door {
    return Door(
        favorites = favorites ?: false,
        id = id ?: Random.nextInt(),
        name = name ?: "",
        room = room ?: "",
        snapshot = snapshot ?: ""
    )
}
