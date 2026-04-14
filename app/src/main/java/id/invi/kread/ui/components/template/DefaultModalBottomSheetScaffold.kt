package id.invi.kread.ui.components.template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.invi.kread.ui.theme.KreadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultModalBottomSheetScaffold(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    DefaultModalBottomSheet(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        Scaffold(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(KreadTheme.extendedColors.backgroundBottomSheet)
                .navigationBarsPadding()
                .fillMaxSize(),
            containerColor = KreadTheme.extendedColors.backgroundBottomSheet,
            contentWindowInsets = WindowInsets(),
            topBar = topBar,
            bottomBar = bottomBar,
            snackbarHost = snackbarHost,
            content = content,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DefaultModalBottomSheetScaffoldPreview() {
    KreadTheme {
        DefaultModalBottomSheetScaffold(
            onDismissRequest = {},
        ) {}
    }
}