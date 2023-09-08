package com.azatberdimyradov.myhome.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.azatberdimyradov.myhome.data.local.LocalDatabase
import com.azatberdimyradov.myhome.data.remote.RemoteSource
import com.azatberdimyradov.myhome.data.remote.Result
import com.azatberdimyradov.myhome.data.toCamera
import com.azatberdimyradov.myhome.data.toDoor
import com.azatberdimyradov.myhome.domain.model.Camera
import com.azatberdimyradov.myhome.domain.model.Door
import com.azatberdimyradov.myhome.domain.repository.MyHomeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyHomeRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localDatabase: LocalDatabase
) : MyHomeRepository {

    private val _errorMessage = MutableSharedFlow<String>()
    override val errorMessage: SharedFlow<String>
        get() = _errorMessage.asSharedFlow()

    init {
        if (localDatabase.databaseIsEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                updateCameras()
                updateDoors()
            }
        }
    }

    override suspend fun updateCameras() {
        when (
            val result = remoteSource.getCameras()
        ) {
            is Result.Error -> _errorMessage.emit(result.message ?: "Something went wrong")

            is Result.Success -> {
                if (result.data?.success == true) {
                    withContext(Dispatchers.Main) {
                        localDatabase.deleteAllCameras()
                        result.data._data?.cameras?.forEach(localDatabase::addCamera)
                    }
                } else {
                    _errorMessage.emit(result.message ?: "Something went wrong")
                }
            }
        }
    }

    override suspend fun updateDoors() {
        when (
            val result = remoteSource.getDoors()
        ) {
            is Result.Error -> _errorMessage.emit(result.message ?: "Something went wrong")

            is Result.Success -> {
                if (result.data?.success == true) {
                    withContext(Dispatchers.Main) {
                        localDatabase.deleteAllDoors()
                        result.data._data?.forEach(localDatabase::addDoor)
                    }
                } else {
                    _errorMessage.emit(result.message ?: "Something went wrong")
                }
            }
        }
    }

    override fun getCameras(): Flow<List<Camera>> {
        return localDatabase.getAllCameras().map { list ->
            list.map { it.toCamera() }
        }
    }

    override fun getDoors(): Flow<List<Door>> {
        return localDatabase.getAllDoors().map { list ->
            list.map { it.toDoor() }
        }
    }
}