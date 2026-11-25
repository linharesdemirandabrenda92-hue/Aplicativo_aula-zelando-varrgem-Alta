package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CityRepository
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppScreen {
    LOGIN,
    HOME,
    SERVICES_MENU,
    NEW_REQUEST,
    REQUEST_DETAIL,
    NEWS,
    JOBS,
    EVENTS,
    MESSAGES,
    PROFILE,
    EVALUATION,
    FULL_MENU
}

class CityViewModel(private val repository: CityRepository = CityRepository()) : ViewModel() {

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _screenHistory = mutableListOf<AppScreen>()

    val requests: StateFlow<List<ServiceRequest>> = repository.requests
    val news: StateFlow<List<NewsItem>> = repository.news
    val jobs: StateFlow<List<JobOpening>> = repository.jobs
    val events: StateFlow<List<CityEvent>> = repository.events
    val messages: StateFlow<List<UserMessage>> = repository.messages
    val profile: StateFlow<CitizenProfile> = repository.profile

    fun login(cpfOrEmail: String, name: String? = null) {
        _isUserLoggedIn.value = true
        if (!name.isNullOrBlank()) {
            val current = profile.value
            repository.updateProfile(current.copy(fullName = name, email = if (cpfOrEmail.contains("@")) cpfOrEmail else current.email))
        }
        _screenHistory.clear()
        _currentScreen.value = AppScreen.HOME
    }

    fun logout() {
        _isUserLoggedIn.value = false
        _screenHistory.clear()
        _currentScreen.value = AppScreen.LOGIN
    }

    private val _selectedRequest = MutableStateFlow<ServiceRequest?>(null)
    val selectedRequest: StateFlow<ServiceRequest?> = _selectedRequest.asStateFlow()

    private val _submissionSuccessMessage = MutableStateFlow<String?>(null)
    val submissionSuccessMessage: StateFlow<String?> = _submissionSuccessMessage.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            _screenHistory.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (_screenHistory.isNotEmpty()) {
            _currentScreen.value = _screenHistory.removeAt(_screenHistory.size - 1)
            return true
        } else if (_currentScreen.value != AppScreen.HOME) {
            _currentScreen.value = AppScreen.HOME
            return true
        }
        return false
    }

    fun openRequestDetail(request: ServiceRequest) {
        _selectedRequest.value = request
        navigateTo(AppScreen.REQUEST_DETAIL)
    }

    fun openEvaluation(request: ServiceRequest) {
        _selectedRequest.value = request
        navigateTo(AppScreen.EVALUATION)
    }

    fun submitServiceRequest(
        category: ServiceCategory,
        description: String,
        address: String,
        latitude: Double,
        longitude: Double,
        photoUri: String?
    ) {
        val newReq = repository.addServiceRequest(
            category = category,
            description = description,
            address = address,
            latitude = latitude,
            longitude = longitude,
            photoUri = photoUri
        )
        _selectedRequest.value = newReq
        _submissionSuccessMessage.value = "Protocolo nº ${newReq.protocolNumber} gerado com sucesso!"
        navigateTo(AppScreen.REQUEST_DETAIL)
    }

    fun clearSuccessMessage() {
        _submissionSuccessMessage.value = null
    }

    fun submitRating(requestId: String, rating: Int, comment: String) {
        repository.submitEvaluation(requestId, rating, comment)
        _selectedRequest.value = _selectedRequest.value?.copy(
            feedbackRating = rating,
            feedbackComment = comment
        )
        navigateBack()
    }

    fun sendCitizenMessage(text: String) {
        if (text.isNotBlank()) {
            repository.sendMessage(text)
        }
    }

    fun updateProfile(profile: CitizenProfile) {
        repository.updateProfile(profile)
    }
}
