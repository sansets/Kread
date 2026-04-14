package id.invi.kread.presenter.login

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.invi.kread.R
import id.invi.kread.ui.components.atom.DefaultTextField
import id.invi.kread.ui.components.atom.PasswordTextField
import id.invi.kread.ui.components.atom.PrimaryButton
import id.invi.kread.ui.components.melecule.LoadingDialog
import id.invi.kread.ui.theme.KreadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    isShowLoadingDialog: Boolean,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    val (email, onEmailChange) = rememberSaveable { mutableStateOf("") }
    val (password, onPasswordChange) = rememberSaveable { mutableStateOf("") }
    val isLoginEnabled = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && password.isNotEmpty()

    if (isShowLoadingDialog) {
        LoadingDialog()
    }

    Scaffold(
        modifier = modifier,

        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Spacer(
                modifier = Modifier.weight(0.75f),
            )
            Text(
                text = stringResource(R.string.title_login),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(
                modifier = Modifier.weight(0.25f),
            )
            DefaultTextField(
                value = email,
                onValueChange = onEmailChange,
                label = stringResource(R.string.title_email),
                placeholder = stringResource(R.string.hint_enter_email),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                )
            )
            PasswordTextField(
                modifier = Modifier.padding(top = 16.dp),
                value = password,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.title_password),
                placeholder = stringResource(R.string.hint_enter_password),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                )
            )
            PrimaryButton(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.title_login),
                enabled = isLoginEnabled,
                onClick = {
                    onLoginClick.invoke(email, password)
                },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.question_did_not_have_an_account),
                    fontSize = 14.sp,
                )
                Spacer(
                    modifier = Modifier.width(4.dp),
                )
                Text(
                    modifier = Modifier.clickable {
                        onRegisterClick.invoke()
                    },
                    text = stringResource(R.string.title_register),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    KreadTheme {
        LoginScreen(
            snackbarHostState = SnackbarHostState(),
            isShowLoadingDialog = false,
            onLoginClick = { _, _ -> },
            onRegisterClick = {},
        )
    }
}
