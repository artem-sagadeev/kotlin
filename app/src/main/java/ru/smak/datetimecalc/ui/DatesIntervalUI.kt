package ru.smak.datetimecalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.smak.datetimecalc.viewmodels.DateIntervalViewModel
import ru.smak.datetimecalc.R
import ru.smak.datetimecalc.viewmodels.MainViewModel

@Composable
fun DateInterval(
    viewModel: DateIntervalViewModel,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val currentDateMessage = stringResource(R.string.current_date_set)
    Column(modifier = modifier) {
        IntervalResults(
            viewModel.resultList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp, top = 4.dp, start = 4.dp, end = 4.dp),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            background = MaterialTheme.colorScheme.surfaceVariant
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){
            item {
                DateSelector(
                    modifier = Modifier.fillMaxWidth(),
                    date = viewModel.firstDate,
                    title = stringResource(id = R.string.date_picker1_title),
                    onDateReset = {
                        if (viewModel.resetDate1())
                            mainViewModel.showMessageCurrentSet(currentDateMessage)
                    }
                ){
                    viewModel.firstDate = it
                }
            }
            item {
                DateSelector(
                    modifier = Modifier.fillMaxWidth(),
                    date = viewModel.secondDate,
                    title = stringResource(id = R.string.date_picker2_title),
                    onDateReset = {
                        if (viewModel.resetDate2())
                            mainViewModel.showMessageCurrentSet(currentDateMessage)
                    }
                ){
                    viewModel.secondDate = it
                }
            }
        }
    }
}

@Preview(locale = "ru")
@Composable
fun DateIntervalPreview(){
    val vm = viewModel(DateIntervalViewModel::class.java)
    val mvm = viewModel(modelClass = MainViewModel::class.java)
    DateInterval(
        vm,
        mvm,
        Modifier.fillMaxSize()
    )
}