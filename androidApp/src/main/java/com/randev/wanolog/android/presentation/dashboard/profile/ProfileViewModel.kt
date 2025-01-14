package com.randev.wanolog.android.presentation.dashboard.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.randev.core.wrapper.Resource
import com.randev.domain.usecase.auth.CheckUserLoginUseCase
import com.randev.domain.usecase.auth.LoginRequest
import com.randev.domain.usecase.auth.PostLoginUseCase
import com.randev.domain.usecase.user.GetCurrentUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author Raihan Arman
 * @date 05/12/22
 */
class ProfileViewModel(
    private val loginUseCase: PostLoginUseCase,
    private val checkUserLoginUseCase: CheckUserLoginUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {

    var isLoading by mutableStateOf(false)
    var emailState by mutableStateOf("")
    var passwordState by mutableStateOf("")

    private val _observeProfileState: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val observeProfileState = _observeProfileState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setEmailValue(value: String) {
        emailState = value
    }

    fun setEmailPassword(value: String) {
        passwordState = value
    }

    fun checkIsLogin() {
        viewModelScope.launch {
            checkUserLoginUseCase.invoke().collect { status ->
                _observeProfileState.update {
                    it.copy(
                        isLogin = status
                    )
                }
                if (status) {
                    println("Hit get current user")
                    getCurrentUser()
                }
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase.invoke().collect { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        _observeProfileState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success -> {
                        _observeProfileState.update {
                            it.copy(
                                isLoading = false,
                                userModel =
                                    if (!resource.model?.data.isNullOrEmpty()) resource.model?.data?.get(0)
                                    else null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _observeProfileState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.errorMessage
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun postLogin() {
        if (!validateForm())
            return

        viewModelScope.launch {
            loginUseCase.invoke(
                LoginRequest(emailState, passwordState)
            ).collect { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        isLoading = true
                    }
                    is Resource.Success -> {
                        if (!resource.model?.data.isNullOrEmpty()) {
                            _observeProfileState.update {
                                it.copy(
                                    isLoading = true,
                                    userModel = resource.model!!.data[0]
                                )
                            }
                            _eventFlow.emit(UIEvent.ShowSnackbar("Login successfully"))
                        }
                        isLoading = false
                    }
                    is Resource.Error -> {
                        _observeProfileState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.errorMessage
                            )
                        }
                        _eventFlow.emit(UIEvent.ShowSnackbar(resource.errorMessage))
                        isLoading = false
                    }
                    else -> {}
                }
            }
        }

    }

    private fun validateForm(): Boolean {
        return (emailState.isNotEmpty() && passwordState.isNotEmpty())
    }

    sealed class UIEvent{
        data class ShowSnackbar(val message: String): UIEvent()
    }

}