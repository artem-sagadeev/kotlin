package ru.smak.datetimecalc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import ru.smak.datetimecalc.R
import ru.smak.datetimecalc.viewmodels.DateAddViewModel
import ru.smak.datetimecalc.viewmodels.MainViewModel

@Composable
fun DatesAddUI(
    viewModel: DateAddViewModel,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val currentDateMessage = stringResource(R.string.current_date_set)
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateSelector(
                date = viewModel.resultDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                clickable = false,
                showTodayButton = false,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                background = MaterialTheme.colorScheme.surfaceVariant,
                title = stringResource(R.string.result)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){
            item {
                DateSelector(
                    modifier = Modifier.fillMaxWidth(),
                    date = viewModel.inputDate,
                    title = stringResource(id = R.string.date_picker1_title),
                    onDateReset = {
                        if (viewModel.resetInput())
                            mainViewModel.showMessageCurrentSet(currentDateMessage)
                    }
                ){
                    viewModel.inputDate = it
                }
            }
            item {
                DateAddParams(
                    viewModel,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(locale = "ru")
@Composable
fun DateAddPreview(){
    val vm = viewModel(DateAddViewModel::class.java)
    val mvm = viewModel(MainViewModel::class.java)
    DatesAddUI(
        vm,
        mvm,
        Modifier.fillMaxSize()
    )
}

@Composable
fun DateAddParams(
    viewModel: DateAddViewModel,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AddSubtractSwitcher(
                viewModel.isAddAction,
                modifier = Modifier.fillMaxWidth(),
                onActionChanged = { viewModel.isAddAction = it }
            )
            OutlinedUIntField(
                value = viewModel.days.toString().replace(Regex("^0$"),""),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = stringResource(id = R.string.date_result_in_days, ""),
                onValueChanged = { viewModel.days = it.toLongOrNull() ?: 0 }
            )
            OutlinedUIntField(
                value = viewModel.weeks.toString().replace(Regex("^0$"),""),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = stringResource(id = R.string.date_result_in_weeks, ""),
                onValueChanged = { viewModel.weeks = it.toLongOrNull() ?: 0 }
            )
            OutlinedUIntField(
                value = viewModel.months.toString().replace(Regex("^0$"),""),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = stringResource(id = R.string.date_result_in_months, ""),
                onValueChanged = { viewModel.months = it.toLongOrNull() ?: 0 }
            )
            OutlinedUIntField(
                value = viewModel.years.toString().replace(Regex("^0$"),""),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = stringResource(id = R.string.date_result_in_years, ""),
                onValueChanged = { viewModel.years = it.toLongOrNull() ?: 0 }
            )
        }
    }
}


@Preview(locale = "ru")
@Composable
fun DateAddParamsPreview(){
    val vm = viewModel(DateAddViewModel::class.java)
    DateAddParams(viewModel = vm)
}