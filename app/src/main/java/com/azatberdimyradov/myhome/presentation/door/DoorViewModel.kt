package com.azatberdimyradov.myhome.presentation.door

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azatberdimyradov.myhome.domain.repository.MyHomeRepository
import com.azatberdimyradov.myhome.domain.use_case.GetDoorsUseCase
import com.azatberdimyradov.myhome.domain.use_case.UpdateDoorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorViewModel @Inject constructor(
    val repo: MyHomeRepository,
    val getDoorsUseCase: GetDoorsUseCase,
    val updateDoorsUseCase: UpdateDoorsUseCase
) : ViewModel() {

    var state by mutableStateOf(DoorScreenState())
        private set

    init {
        getDoors()
        fetchError()
    }

    fun updateDoors() = viewModelScope.launch {
        updateDoorsUseCase()
    }

    private fun getDoors() {
        getDoorsUseCase().onEach {
            state = state.copy(doors = it)
        }.launchIn(viewModelScope)
    }

    private fun fetchError() {
        repo.errorMessage.onEach {
            state = state.copy(errorMessage = it)
            delay(4000L)
            state = state.copy(errorMessage = "")
        }.launchIn(viewModelScope)
    }
}