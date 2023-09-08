package com.azatberdimyradov.myhome.domain.use_case

import com.azatberdimyradov.myhome.domain.model.Door
import com.azatberdimyradov.myhome.domain.repository.MyHomeRepository
import kotlinx.coroutines.flow.Flow

class GetDoorsUseCase constructor(
    private val repo: MyHomeRepository
) {
    operator fun invoke(): Flow<List<Door>> {
        return repo.getDoors()
    }
}