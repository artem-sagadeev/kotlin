package ru.smak.datetimecalc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.smak.datetimecalc.ui.theme.DateTimeCalcTheme
import ru.smak.datetimecalc.viewmodels.DateAddViewModel
import ru.smak.datetimecalc.viewmodels.DateIntervalViewModel
import ru.smak.datetimecalc.viewmodels.MainViewModel
import ru.smak.datetimecalc.viewmodels.TimeIntervalViewModel
import ru.smak.datetimecalc.viewmodels.ViewPage

@Composable
fun MainUI(
    mainViewModel: MainViewModel,
    diViewModel: DateIntervalViewModel,
    daViewModel: DateAddViewModel,
    tiViewModel: TimeIntervalViewModel,
){
    DateTimeCalcTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DtcTopAppBar(mainViewModel.viewPage)
            },
            bottomBar = {
                DtcBottomAppBar(mainViewModel.viewPage){
                    mainViewModel.viewPage = it
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = mainViewModel.snackbarHostState)
            },
            floatingActionButton = {

            },
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            Box(modifier = Modifier.padding(it)){
                when(mainViewModel.viewPage){
                    ViewPage.DATE_INTERVAL -> DateInterval(diViewModel, mainViewModel, Modifier.fillMaxSize())
                    ViewPage.DATE_ADD -> DatesAddUI(daViewModel, mainViewModel, Modifier.fillMaxSize())
                    ViewPage.TIME -> TimeUI(tiViewModel, mainViewModel, Modifier.fillMaxSize())
                }
            }
        }
    }
}


@Preview(locale = "ru")
@Composable
fun MainUIPreview(){
    val mvm = viewModel(MainViewModel::class.java)
    val divm = viewModel(DateIntervalViewModel::class.java)
    val davm = viewModel(DateAddViewModel::class.java)
    val tvm = viewModel(TimeIntervalViewModel::class.java)
    MainUI(mvm, divm, davm, tvm)
}

/**
 * Функция композиции для отображения нижней панели приложения с вкладками для различных ViewPage.
 * @param currentViewPage Текущая выбранная страница просмотра
 * @param onChangeTab Callback функция  для обработки изменения вкладки
 */
@Composable
fun DtcBottomAppBar(
    currentViewPage: ViewPage,
    onChangeTab: (ViewPage)->Unit = {},
) {
    // Строка, используемая для хранения вкладок меню 
    TabRow(
        currentViewPage.ordinal,
    ){
        // Цикл, добавляющий вкладки
        for (viewPage in ViewPage.entries) {
            // Для каждой вкладки добавляется элемент Tab
            Tab(
                selected = currentViewPage == viewPage,
                onClick = { onChangeTab(viewPage) },
                modifier = Modifier.padding(vertical = 8.dp),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.secondary,
            ) {
                // Для 
                Icon(
                    painter = painterResource(id = getNavIconId(viewPage)),
                    contentDescription = stringResource(id = getPageTitleId(viewPage)),
                    tint = colorResource(id = getNavIconTint(viewPage = viewPage)),
                )
                Text(
                    text = stringResource(id = getNavTitleId(viewPage)),
                    fontWeight = if (currentViewPage == viewPage) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DtcTopAppBar(viewPage: ViewPage) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = getPageTitleId(viewPage = viewPage)))
        },
        navigationIcon = {
            IconButton(
                onClick = { },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = getNavIconId(viewPage = viewPage)),
                    contentDescription = stringResource(id = getPageTitleId(viewPage = viewPage)),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Preview(locale = "ru")
@Composable
fun DtcTopAppBarPreview(){
    DtcTopAppBar(ViewPage.DATE_INTERVAL)
}

@Preview(locale = "ru")
@Composable
fun DtcBottomAppBarPreview(){
    DtcBottomAppBar(ViewPage.DATE_INTERVAL)
}