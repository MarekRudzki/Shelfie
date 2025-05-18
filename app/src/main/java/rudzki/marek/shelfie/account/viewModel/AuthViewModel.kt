package rudzki.marek.shelfie.account.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rudzki.marek.shelfie.account.repositories.AuthRepository

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
): ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun logout() {
        _uiState.value = AuthUiState.LoadingLogout

        repo.logout()
            .addOnSuccessListener {
            _uiState.value = AuthUiState.LoggedOut
        }
            .addOnFailureListener { e ->
                _uiState.value = AuthUiState.Error(e.message ?: "Error during log out")
            }
    }

    fun deleteAccount() {
        _uiState.value = AuthUiState.LoadingDelete

        repo.deleteUser()
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