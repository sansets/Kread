package id.invi.kread.presenter.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.invi.kread.ui.theme.KreadTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    KreadTheme {
        SplashScreen()
    }
}
