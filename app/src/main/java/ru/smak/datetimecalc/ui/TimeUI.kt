package ru.smak.datetimecalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.smak.datetimecalc.R
import ru.smak.datetimecalc.viewmodels.MainViewModel
import ru.smak.datetimecalc.viewmodels.TimeIntervalViewModel

@Composable
fun TimeUI(
    viewModel: TimeIntervalViewModel,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val currentTimeMessage = stringResource(R.string.current_time_set)
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IntervalResults(
                items = viewModel.resultList,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, top = 4.dp, start = 4.dp, end = 4.dp),
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                background = MaterialTheme.colorScheme.surfaceVariant
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){
            item {
                TimeSelector(
                    modifier = Modifier.fillMaxWidth(),
                    time = viewModel.inputTime1,
                    title = stringResource(id = R.string.time_picker1_title),
                    onTimeReset = {
                        if (viewModel.resetStartTime())
                            mainViewModel.showMessageCurrentSet(currentTimeMessage)
                    }
                ){
                    viewModel.inputTime1 = it
                }
            }
            item {
                TimeSelector(
                    modifier = Modifier.fillMaxWidth(),
                    time = viewModel.inputTime2,
                    title = stringResource(id = R.string.time_picker2_title),
                    onTimeReset = {
                        if (viewModel.resetFinishTime())
                            mainViewModel.showMessageCurrentSet(currentTimeMessage)
                    }
                ){
                    viewModel.inputTime2 = it
                }
            }
        }
    }
}

@Preview(locale = "ru")
@Composable
fun TimeUIPreview(){
    val vm = viewModel(TimeIntervalViewModel::class.java)
    val mvm = viewModel(MainViewModel::class.java)
    TimeUI(
        vm,
        mvm,
        Modifier.fillMaxWidth()
    )
}
