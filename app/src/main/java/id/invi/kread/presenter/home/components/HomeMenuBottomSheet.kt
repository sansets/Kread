package id.invi.kread.presenter.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.invi.kread.R
import id.invi.kread.ui.components.atom.DangerOutlinedButton
import id.invi.kread.ui.components.organism.BottomSheetTopAppBar
import id.invi.kread.ui.components.template.DefaultModalBottomSheet
import id.invi.kread.ui.theme.KreadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMenuBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    DefaultModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .background(KreadTheme.extendedColors.backgroundBottomSheet)
                .navigationBarsPadding()
        ) {
            BottomSheetTopAppBar(
                onCloseClick = onDismiss,
            )
            HomeMenuContent(
                onLogoutClick = onLogoutClick,
            )
        }
    }
}

@Composable
fun HomeMenuContent(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        DangerOutlinedButton(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.action_logout),
            onClick = onLogoutClick,
        )
    }
}

@Preview
@Composable
private fun HomeMenuBottomSheetPreview() {
    KreadTheme {
        HomeMenuBottomSheet(
            onDismiss = {},
            onLogoutClick = {},
        )
    }
}
