package com.azatberdimyradov.myhome.domain.repository

import androidx.lifecycle.LiveData
import com.azatberdimyradov.myhome.domain.model.Camera
import com.azatberdimyradov.myhome.domain.model.Door
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MyHomeRepository {

    val errorMessage: SharedFlow<String>

    suspend fun updateCameras()

    suspend fun updateDoors()

    fun getCameras(): LiveData<List<Camera>>

    fun getDoors(): LiveData<List<Door>>

}