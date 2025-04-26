package rudzki.marek.shelfie.login.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rudzki.marek.shelfie.R

fun formatPhoneNumber(input: String): String {
    return input.chunked(3).joinToString(" ")
}

@Composable
fun PhoneNumberInput(
    isPhoneNumberValid: Boolean,
    onPhoneNumberValidation: (Boolean) -> Unit,
) {
    var phoneNumber by remember { mutableStateOf("") }
    var phoneNumberField by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = phoneNumberField,
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor =  MaterialTheme.colorScheme.primary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color(255,255,255),
            unfocusedContainerColor = Color(255,255,255),
            errorContainerColor = Color(255,255,255),
            focusedTextColor = MaterialTheme.colorScheme.primary,
            errorTextColor = MaterialTheme.colorScheme.primary,
        ),

        leadingIcon = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pl_flag),
                    contentDescription = "Poland Flag",
                    modifier = Modifier
                        .height(22.dp)
                        .width(35.dp)
                        .border(width = 1.dp, color = Color.Black)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(+48)",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                )
            }
        },
        onValueChange = { newValue ->
            val digitsOnly = newValue.text.filter { it.isDigit() }

            if (digitsOnly.length <= 9) {
                phoneNumber = digitsOnly

                val formatted = formatPhoneNumber(digitsOnly)

                phoneNumberField = TextFieldValue(
                    text = formatted,
                    selection = TextRange(formatted.length) // Kursor na końcu
                )
            }

            if (phoneNumber.length == 9) {
                onPhoneNumberValidation(true)
            } else {
                onPhoneNumberValidation(false)
            }
        },
        label = {
            Text(
                text = "Your phone number",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        },
        isError = !isPhoneNumberValid,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        )
    )

    if (!isPhoneNumberValid) {
        Text(
            text = "Phone number must be exactly 9 digits",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}