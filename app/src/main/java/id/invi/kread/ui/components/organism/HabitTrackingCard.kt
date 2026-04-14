package id.invi.kread.ui.components.organism

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.invi.kread.R
import id.invi.kread.domain.Result
import id.invi.kread.ui.components.melecule.HabitTrackingSyncStatus
import id.invi.kread.ui.theme.KreadTheme

@Composable
fun HabitTrackingCard(
    modifier: Modifier = Modifier,
    bookTitle: String,
    readingDate: String,
    readingStartTime: String,
    readingEndTime: String,
    syncStatus: Result<Unit>,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(
                modifier = modifier.weight(1f),
            ) {
                Text(
                    text = bookTitle,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = readingDate,
                        fontSize = 12.sp,
                        color = KreadTheme.extendedColors.textSecondary,
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(4.dp)
                            .background(KreadTheme.extendedColors.textSecondary),
                    )
                    Text(
                        text = "$readingStartTime - $readingEndTime",
                        fontSize = 12.sp,
                        color = KreadTheme.extendedColors.textSecondary,
                    )
                }
            }
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.round_arrow_forward_ios_24),
                contentDescription = null,
                tint = KreadTheme.extendedColors.icon,
            )
        }
        if (syncStatus is Result.Error || syncStatus is Result.Loading) {
            HabitTrackingSyncStatus(
                modifier = Modifier.fillMaxWidth(),
                syncStatus = syncStatus,
            )
        }
        Box(
            modifier = Modifier
                .background(KreadTheme.extendedColors.divider)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HabitTrackingCardPreview() {
    KreadTheme {
        HabitTrackingCard(
            bookTitle = "Habit Tracking 1",
            readingDate = "13 Apr 2026",
            readingStartTime = "13:00",
            readingEndTime = "13:30",
            syncStatus = Result.Loading,
            onClick = {},
        )
    }
}
