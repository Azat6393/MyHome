package com.azatberdimyradov.myhome.domain.use_case

import androidx.lifecycle.LiveData
import com.azatberdimyradov.myhome.domain.model.Camera
import com.azatberdimyradov.myhome.domain.repository.MyHomeRepository
import kotlinx.coroutines.flow.Flow

class GetCamerasUseCase constructor(
    private val repo: MyHomeRepository
) {
    operator fun invoke(): Flow<List<Camera>> {
        return repo.getCameras()
    }
}