package id.invi.kread.presenter.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.invi.kread.R
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.domain.model.getDummyHabitTrackings
import id.invi.kread.ui.components.atom.DangerOutlinedButton
import id.invi.kread.ui.components.atom.PrimaryButton
import id.invi.kread.ui.components.organism.BottomSheetTopAppBar
import id.invi.kread.ui.components.template.DefaultModalBottomSheet
import id.invi.kread.ui.theme.KreadTheme
import id.invi.kread.util.formatToDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackingActionBottomSheet(
    modifier: Modifier = Modifier,
    habitTracking: HabitTracking,
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
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
                title = {
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = habitTracking.bookTitle,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = habitTracking.readingDate.formatToDisplay(),
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
                                text = "${habitTracking.readingStartTime} - ${habitTracking.readingEndTime}",
                                fontSize = 12.sp,
                                color = KreadTheme.extendedColors.textSecondary,
                            )
                        }
                    }
                }
            )
            HabitTrackingActionContent(
                onDeleteClick = onDeleteClick,
                onEditClick = onEditClick,
            )
        }
    }
}

@Composable
fun HabitTrackingActionContent(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DangerOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.action_delete),
            onClick = onDeleteClick,
        )
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.action_edit),
            onClick = onEditClick,
        )
    }
}

@Preview
@Composable
private fun HabitTrackingActionBottomSheetPreview() {
    KreadTheme {
        HabitTrackingActionBottomSheet(
            habitTracking = getDummyHabitTrackings().first(),
            onDismiss = {},
            onDeleteClick = {},
            onEditClick = {},
        )
    }
}
