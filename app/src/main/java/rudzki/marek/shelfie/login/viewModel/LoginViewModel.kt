package rudzki.marek.shelfie.login.viewModel


import android.app.Activity
import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import rudzki.marek.shelfie.login.model.AuthRepository
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val application: Application
) : AndroidViewModel(application) {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    var phoneNumber by mutableStateOf("")
        private set

    var isPhoneNumberValid by mutableStateOf(false)
        private set

    var isSmsSent by mutableStateOf(false)
        private set

    private var verificationId: String? = null

    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    var isLoading by mutableStateOf(false)
        private set

    fun updateLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun updatePhoneNumber(number: String) {
        phoneNumber = "+48${number.trim()}"
    }

    fun updatePhoneNumberValid(valid: Boolean) {
        isPhoneNumberValid = valid
    }

    fun updateIsSmsSent(isSend: Boolean) {
        isSmsSent = isSend
    }

    fun startVerification(activity: Activity, onCodeSent: () -> Unit) {
        updateLoading(true)
        repository.startPhoneNumberVerification(
            activity = activity,
            phoneNumber = phoneNumber,
            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    updateLoading(false)
                    viewModelScope.launch {
                        _uiEvent.emit(UiEvent.LoginError("Verification failed: ${e.localizedMessage}"))
                    }
                }

                override fun onCodeSent(vId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    verificationId = vId
                    resendToken = token
                    updateIsSmsSent(true)
                    updateLoading(false)
                    onCodeSent()
                }
            }
        )
    }

    fun verifyCode(code: String) {
        val vId = verificationId
        if (vId != null) {
            val credential = PhoneAuthProvider.getCredential(vId, code)
            signInWithCredential(credential)
        } else {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.LoginError("Verification ID is null. Try restarting the process."))
            }
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        repository.signInWithCredential(credential) { success, exception ->
            viewModelScope.launch {
                if (success) {
                    _uiEvent.emit(UiEvent.LoginSuccess)
                } else {
                    val message = exception?.localizedMessage ?: "Unknown error during login"
                    _uiEvent.emit(UiEvent.LoginError(message))
                }
            }
        }
    }

    fun resendVerificationCode(activity: Activity) {
        val token = resendToken
        if (token != null) {
            repository.startPhoneNumberVerification(
                activity = activity,
                phoneNumber = phoneNumber,
                token = token,
                callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        signInWithCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        viewModelScope.launch {
                            _uiEvent.emit(UiEvent.LoginError("Resend failed: ${e.localizedMessage}"))
                        }
                    }

                    override fun onCodeSent(vId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        verificationId = vId
                        resendToken = token
                        updateIsSmsSent(true)
                    }
                }
            )
        } else {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.LoginError("Cannot resend code yet. Please try again later."))
            }
        }
    }
}


sealed class UiEvent {
    object LoginSuccess : UiEvent()
    data class LoginError(val message: String) : UiEvent()
}