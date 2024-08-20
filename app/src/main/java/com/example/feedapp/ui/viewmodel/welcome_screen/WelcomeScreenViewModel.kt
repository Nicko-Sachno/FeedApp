package com.example.feedapp.ui.viewmodel.welcome_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedapp.domain.repository.WelcomeScreenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WelcomeScreenViewModel(
    private val repository: WelcomeScreenRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(
            name = "Welcome to the Feed App",
            date = LocalDateTime.now().date,
            label = ""
        )
    )
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            repository.fetchWelcomeScreenData().collect { data ->
                _uiState.value = _uiState.value
                    .copy(label = data.label)
            }
        }
    }

    fun updateDate() {
        _uiState.value = _uiState.value.copy(date = LocalDateTime.now().date)
    }
}

val LocalDateTime.date: String
    get() = this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))

data class UiState(
    val name: String,
    val date: String,
    val label: String,
)