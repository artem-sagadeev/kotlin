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
 * Функция композиции для отображения нижней панели приложения с вкладками для различных страниц.
 * @param currentViewPage Текущая выбранная страница просмотра
 * @param onChangeTab Функция для обработки изменения вкладки
 */
@Composable
fun DtcBottomAppBar(
    currentViewPage: ViewPage,
    onChangeTab: (ViewPage)->Unit = {},
) {
    // Контейнер, используемый для хранения вкладок меню 
    TabRow(
        currentViewPage.ordinal,
    ){
        // Цикл, добавляющий вкладки
        for (viewPage in ViewPage.entries) {
            // Для каждой вкладки добавляется элемент Tab, соответствующий странице приложения
            Tab(
                // Вкладка отмечается как выбранная если соответствующая ей страница - текущая открытая
                selected = currentViewPage == viewPage,
                // При нажатии на вкладку происходит смена страницы
                onClick = { onChangeTab(viewPage) },
                modifier = Modifier.padding(vertical = 8.dp),
                // Задаются цвета для нижней панели
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.secondary,
            ) {
                // Элемент Tab состоит из элементов Icon (иконка) и элемента Text (текстовое поле)
                Icon(
                    // painter получаем из ресурсов по текущей странице
                    painter = painterResource(id = getNavIconId(viewPage)),
                    // Описание иконки получаем из ресурсов по текцщей странице
                    contentDescription = stringResource(id = getPageTitleId(viewPage)),
                    // Оттенок получаем из ресурсов по текущей странице
                    tint = colorResource(id = getNavIconTint(viewPage = viewPage)),
                )
                Text(
                    // Текст получаем из ресурсов по идентификатору текущей страницы
                    text = stringResource(id = getNavTitleId(viewPage)),
                    // Название текущей страницы выделяется жирным шрифтом
                    fontWeight = if (currentViewPage == viewPage) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}

/**
 * Функция композиции для отображения верхней панели приложения.
 * @param title название текущей страницы
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DtcTopAppBar(viewPage: ViewPage) {
    // Добавляется верхняя панель приложения
    TopAppBar(
        // Задается текст, соответствующий названию страницы
        title = {
            // Текст получаем из ресурсов по идентификатору текущей страницы
            Text(text = stringResource(id = getPageTitleId(viewPage = viewPage)))
        },
        // Задается иконка для открытия панели навигации
        navigationIcon = {
            IconButton(
                onClick = { },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    // painter получаем из ресурсов по текущей странице
                    painter = painterResource(id = getNavIconId(viewPage = viewPage)),
                    // Описание иконки получаем из ресурсов по текцщей странице
                    contentDescription = stringResource(id = getPageTitleId(viewPage = viewPage)),
                )
            }
        },
        // Задаются цвета для верхней панели
        colors = TopAppBarDefaults.topAppBarColors(
            // Цвет контейнера
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            // Цвет текста в верхней панели
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