package ru.smak.datetimecalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import ru.smak.datetimecalc.ui.MainUI
import ru.smak.datetimecalc.viewmodels.DateAddViewModel
import ru.smak.datetimecalc.viewmodels.DateIntervalViewModel
import ru.smak.datetimecalc.viewmodels.MainViewModel
import ru.smak.datetimecalc.viewmodels.TimeIntervalViewModel

class MainActivity : ComponentActivity() {

    private val dateIntervalViewMode: DateIntervalViewModel by lazy {
        ViewModelProvider(this)[DateIntervalViewModel::class.java]
    }

    private val dateAddViewModel: DateAddViewModel by lazy{
        ViewModelProvider(this)[DateAddViewModel::class.java]
    }

    private val timeIntervalViewMode: TimeIntervalViewModel by lazy {
        ViewModelProvider(this)[TimeIntervalViewModel::class.java]
    }

    private val mainViewModel: MainViewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainUI(
                mainViewModel,
                dateIntervalViewMode,
                dateAddViewModel,
                timeIntervalViewMode,
            )
        }
    }
}