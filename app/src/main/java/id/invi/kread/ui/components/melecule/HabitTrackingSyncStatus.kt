package id.invi.kread.ui.components.melecule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.invi.kread.R
import id.invi.kread.domain.Result
import id.invi.kread.domain.checkResult
import id.invi.kread.ui.theme.KreadTheme

@Composable
fun HabitTrackingSyncStatus(
    modifier: Modifier = Modifier,
    syncStatus: Result<Unit>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(KreadTheme.extendedColors.backgroundCard)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        syncStatus.checkResult(
            onError = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.rounded_sync_disabled_24),
                    contentDescription = null,
                    tint = KreadTheme.extendedColors.icon,
                )
            },
            onLoading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp),
                    strokeWidth = 3.dp,
                )
            },
        )
        Column {
            syncStatus.checkResult(
                onError = {
                    Text(
                        text = stringResource(R.string.title_unsynchronized),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                    )
                },
            )
            Text(
                text = when (syncStatus) {
                    is Result.Loading -> stringResource(R.string.title_synchronizing)
                    is Result.Error -> stringResource(R.string.hint_tap_here_to_synchronize)
                    else -> ""
                },
                fontSize = 11.sp,
                color = KreadTheme.extendedColors.textSecondary,
            )
        }
    }
}

@Preview
@Composable
private fun HabitTrackingSyncStatusLoadingPreview() {
    KreadTheme {
        HabitTrackingSyncStatus(
            syncStatus = Result.Loading,
        )
    }
}

@Preview
@Composable
private fun HabitTrackingSyncStatusErrorPreview() {
    KreadTheme {
        HabitTrackingSyncStatus(
            syncStatus = Result.Error(Exception("")),
        )
    }
}