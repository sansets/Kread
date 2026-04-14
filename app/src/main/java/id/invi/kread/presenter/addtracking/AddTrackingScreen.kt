package id.invi.kread.presenter.addtracking

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.invi.kread.R
import id.invi.kread.domain.EventResult
import id.invi.kread.domain.checkChannelValue
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.ui.components.atom.CalendarTextField
import id.invi.kread.ui.components.atom.DefaultTextField
import id.invi.kread.ui.components.atom.PrimaryButton
import id.invi.kread.ui.components.atom.TimeTextField
import id.invi.kread.ui.components.melecule.LoadingDialog
import id.invi.kread.ui.components.organism.BottomSheetTopAppBar
import id.invi.kread.ui.components.template.DefaultModalBottomSheet
import id.invi.kread.ui.theme.KreadTheme
import id.invi.kread.util.formatToDisplay
import id.invi.kread.util.toDate
import kotlinx.coroutines.channels.Channel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTrackingScreen(
    modifier: Modifier = Modifier,
    habitTracking: HabitTracking?,
    addTrackingEvent: Channel<EventResult<Any>>,
    updateTrackingEvent: Channel<EventResult<Unit>>,
    onDismiss: () -> Unit,
    onSaveClick: (habitTracking: HabitTracking) -> Unit,
    onSaveSuccess: () -> Unit,
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isShowLoadingDialog by remember { mutableStateOf(false) }

    val (bookTitle, setBookTitle) = remember { mutableStateOf(habitTracking?.bookTitle.orEmpty()) }
    val (readingDate, setReadingDate) = remember {
        mutableStateOf(
            habitTracking?.readingDate?.formatToDisplay().orEmpty()
        )
    }
    val (startTime, setStartTime) = remember { mutableStateOf(habitTracking?.readingStartTime.orEmpty()) }
    val (endTime, setEndTime) = remember { mutableStateOf(habitTracking?.readingEndTime.orEmpty()) }

    val isButtonEnabled = bookTitle.isNotBlank()
            && readingDate.isNotBlank()
            && startTime.isNotBlank()
            && endTime.isNotBlank()

    LaunchedEffect(Unit) {
        addTrackingEvent.checkChannelValue(
            onLoading = {
                isShowLoadingDialog = true
            },
            onError = {
                isShowLoadingDialog = false
                Toast.makeText(context, it?.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                isShowLoadingDialog = false
                onSaveSuccess()

                setBookTitle("")
                setReadingDate("")
                setStartTime("")
                setEndTime("")
            },
        )
    }

    LaunchedEffect(Unit) {
        updateTrackingEvent.checkChannelValue(
            onLoading = {
                isShowLoadingDialog = true
            },
            onError = {
                isShowLoadingDialog = false
                Toast.makeText(context, it?.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                isShowLoadingDialog = false
                onSaveSuccess()

                setBookTitle("")
                setReadingDate("")
                setStartTime("")
                setEndTime("")
            },
        )
    }

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
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                DefaultTextField(
                    value = bookTitle,
                    onValueChange = setBookTitle,
                    label = stringResource(R.string.title_book_title),
                    placeholder = stringResource(R.string.hint_enter_book_title),
                )
                CalendarTextField(
                    value = readingDate,
                    onValueChange = setReadingDate,
                    label = stringResource(R.string.title_reading_date),
                    placeholder = stringResource(R.string.hint_enter_reading_date),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    TimeTextField(
                        modifier = Modifier.weight(1f),
                        value = startTime,
                        onValueChange = setStartTime,
                        label = stringResource(R.string.title_start_time),
                        placeholder = stringResource(R.string.hint_enter_start_time),
                    )
                    TimeTextField(
                        modifier = Modifier.weight(1f),
                        value = endTime,
                        onValueChange = setEndTime,
                        label = stringResource(R.string.title_end_time),
                        placeholder = stringResource(R.string.hint_enter_end_time),
                    )
                }
            }
            PrimaryButton(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.action_save),
                enabled = isButtonEnabled,
                onClick = {
                    onSaveClick(
                        HabitTracking(
                            id = "",
                            bookTitle = bookTitle,
                            readingDate = readingDate.toDate() ?: Date(),
                            readingStartTime = startTime,
                            readingEndTime = endTime,
                            isSynchronized = false
                        )
                    )
                },
            )
        }
    }

    if (isShowLoadingDialog) {
        LoadingDialog()
    }
}

@Preview
@Composable
private fun AddTrackingPreview() {
    KreadTheme {
        AddTrackingScreen(
            onDismiss = {},
            habitTracking = null,
            addTrackingEvent = Channel(),
            updateTrackingEvent = Channel(),
            onSaveClick = {},
            onSaveSuccess = {},
        )
    }
}