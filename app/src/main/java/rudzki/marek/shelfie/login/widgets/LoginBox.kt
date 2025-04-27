package rudzki.marek.shelfie.login.widgets

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import rudzki.marek.shelfie.shared.TopBarWithBackButton
import java.util.concurrent.TimeUnit

@Composable
fun LoginBox(
    onLoginBoxChanged: (Int) -> Unit,
) {
    var phoneNumberCorrect by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("")}
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val activity = context as Activity

    var verificationId by remember { mutableStateOf<String?>(null) }
    var smsCode by remember { mutableStateOf("") }
    var isSmsSent by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clip(RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp))
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarWithBackButton(
                title = "Login",
                showIcon = false,
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Please type your phone number to login or register."
            )
            Spacer(modifier = Modifier.height(12.dp))
            PhoneNumberInput(
                isPhoneNumberValid = phoneNumberCorrect,
                onPhoneNumberValidation = {phoneNumberCorrect = it},
                onPhoneNumberChange = { phoneNumber = "+48${it.trim()}"}
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "We will send a SMS to verify your account"
            )
//            Spacer(modifier = Modifier
//                .fillMaxHeight(0.35f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(16.dp),
                onClick = {
println("ZZZ NUMBER : $phoneNumber")
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(activity)
                        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                // Automatyczne zalogowanie, jeśli SMS zostanie wykryty automatycznie
                                auth.signInWithCredential(credential)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // Sukces - użytkownik zalogowany
                                            Log.d("FirebaseAuth", "Zalogowano automatycznie!")
                                        } else {
                                            // Błąd logowania
                                            Log.e("FirebaseAuth", "Błąd automatycznego logowania", task.exception)
                                        }
                                    }
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                Log.e("FirebaseAuth", "Weryfikacja nieudana", e)
                            }

                            override fun onCodeSent(vId: String, token: PhoneAuthProvider.ForceResendingToken) {
                                // SMS wysłany
                                verificationId = vId
                                isSmsSent = true
                                Log.d("FirebaseAuth", "Kod SMS wysłany!")
                            }
                        })
                        .build()

                    PhoneAuthProvider.verifyPhoneNumber(options)

//                    if (isSmsSent) {
//                        onLoginBoxChanged(1)
//                    }
                },
                enabled = phoneNumberCorrect,
                colors = ButtonDefaults.buttonColors(

                ),
            ) {
                Text("Confirm")
            }

            if (isSmsSent) {
                OutlinedTextField(
                    value = smsCode,
                    onValueChange = { smsCode = it },
                    label = { Text("Kod SMS") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                Button(
                    onClick = {
                        verificationId?.let { id ->
                            val credential = PhoneAuthProvider.getCredential(id, smsCode)

                            auth.signInWithCredential(credential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Sukces - użytkownik zalogowany
                                        Log.d("FirebaseAuth", "Użytkownik zalogowany ręcznie!")
                                    } else {
                                        // Błąd kodu
                                        Log.e("FirebaseAuth", "Błąd kodu SMS", task.exception)
                                    }
                                }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Zaloguj się")
                }
            }
        }
    }
}