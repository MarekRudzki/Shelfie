package rudzki.marek.shelfie.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rudzki.marek.shelfie.home.viewModel.AuthViewModel

@Composable
fun HomeAppBar(
    viewModel: AuthViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(start = 48.dp),
            text = "Shelfie",
            style = androidx.compose.ui.text.TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W600,
                fontSize = 22.sp,
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Box {
            IconButton(
                onClick = { expanded = true}
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings Button",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {expanded = false}
            ) {
                DropdownMenuItem(
                    text = { Text("Log Out") },
                    onClick = {
                        expanded = false
                        viewModel.logout()
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text("Delete Account")
                    },
                    onClick = {
                        expanded = false
                        viewModel.deleteAccount()
                    }
                )
            }
        }
    }
}