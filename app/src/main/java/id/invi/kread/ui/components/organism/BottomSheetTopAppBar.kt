package id.invi.kread.ui.components.organism

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.invi.kread.R
import id.invi.kread.ui.components.atom.DefaultIconButton
import id.invi.kread.ui.theme.KreadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onCloseClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    BottomSheetTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = KreadTheme.extendedColors.textPrimary,
            )
        },
        onCloseClick = onCloseClick,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    onCloseClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = modifier.background(KreadTheme.extendedColors.backgroundBottomSheet),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .width(33.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(KreadTheme.extendedColors.divider),
        )
        CenterAlignedTopAppBar(
            modifier = Modifier.heightIn(56.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = KreadTheme.extendedColors.backgroundBottomSheet,
            ),
            title = title,
            actions = {
                DefaultIconButton(
                    iconRes = R.drawable.rounded_close_24,
                    onClick = onCloseClick,
                )
            }
        )
        content()
    }
}

@Preview
@Composable
fun BottomSheetTopAppBarPreview() {
    KreadTheme {
        BottomSheetTopAppBar(
            onCloseClick = {},
        )
    }
}