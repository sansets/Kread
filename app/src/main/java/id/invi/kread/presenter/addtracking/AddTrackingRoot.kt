package id.invi.kread.presenter.addtracking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import id.invi.kread.domain.model.HabitTracking

@Composable
fun AddTrackingRoot(
    modifier: Modifier = Modifier,
    viewModel: AddTrackingViewModel = hiltViewModel(),
    habitTracking: HabitTracking? = null,
    onDismiss: () -> Unit,
    onAddTrackingSuccess: () -> Unit,
) {
    val addTrackingEvent = remember { viewModel.addTrackingEvent }
    val updateTrackingEvent = remember { viewModel.updateTrackingEvent }

    AddTrackingScreen(
        modifier = modifier,
        habitTracking = habitTracking,
        addTrackingEvent = addTrackingEvent,
        updateTrackingEvent = updateTrackingEvent,
        onDismiss = onDismiss,
        onSaveClick = {
            if (habitTracking != null) {
                viewModel.updateTracking(it.copy(id = habitTracking.id))
            } else {
                viewModel.addTracking(it)
            }
        },
        onSaveSuccess = {
            onAddTrackingSuccess()
        }
    )
}
