package rudzki.marek.shelfie.home.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rudzki.marek.shelfie.login.model.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun logout() {
        _uiState.value = AuthUiState.LoadingLogout

        repository.logout()
            .addOnSuccessListener {
            _uiState.value = AuthUiState.LoggedOut
        }
            .addOnFailureListener { e ->
                _uiState.value = AuthUiState.Error(e.message ?: "Error during log out")
            }
    }

    fun deleteAccount() {
        _uiState.value = AuthUiState.LoadingDelete

        repository.deleteUser()
            .addOnSuccessListener {
                _uiState.value = AuthUiState.Deleted
            }
            .addOnFailureListener { e ->
                _uiState.value = AuthUiState.Error(e.message ?: "Error during account delete")
            }
    }

    fun resetToIdle() {
        _uiState.value = AuthUiState.Idle
    }
}


sealed class AuthUiState {
    object Idle: AuthUiState()
    object LoadingLogout : AuthUiState()
    object LoadingDelete  : AuthUiState()
    object LoggedOut: AuthUiState()
    object Deleted: AuthUiState()
    data class Error(val message: String): AuthUiState()
}