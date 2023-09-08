package com.azatberdimyradov.myhome.presentation.camera

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azatberdimyradov.myhome.domain.repository.MyHomeRepository
import com.azatberdimyradov.myhome.domain.use_case.GetCamerasUseCase
import com.azatberdimyradov.myhome.domain.use_case.UpdateCamerasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    val repo: MyHomeRepository,
    val getCamerasUseCase: GetCamerasUseCase,
    val updateCamerasUseCase: UpdateCamerasUseCase
) : ViewModel() {

    var state by mutableStateOf(CameraScreenState())
        private set

    init {
        getCameras()
        fetchError()
    }

    fun updateCameras() = viewModelScope.launch {
        updateCamerasUseCase()
        state = state.copy(isLoading = false)
    }

    private fun getCameras() {
        getCamerasUseCase().onEach {
            state = state.copy(cameras = it, isLoading = false)
        }.launchIn(viewModelScope)
    }

    private fun fetchError() {
        repo.errorMessage.onEach {
            state = state.copy(errorMessage = it, isLoading = false)
            delay(4000L)
            state = state.copy(errorMessage = "", isLoading = false)
        }.launchIn(viewModelScope)
    }
}