package ru.smak.datetimecalc.viewmodels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var viewPage: ViewPage by mutableStateOf(ViewPage.DATE_INTERVAL)
    val snackbarHostState = SnackbarHostState()

    fun showMessageCurrentSet(message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}