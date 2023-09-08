package com.azatberdimyradov.myhome.domain.use_case

import com.azatberdimyradov.myhome.domain.repository.MyHomeRepository

class UpdateDoorsUseCase constructor(
    private val repo: MyHomeRepository
) {
    suspend operator fun invoke() {
        repo.updateCameras()
    }
}