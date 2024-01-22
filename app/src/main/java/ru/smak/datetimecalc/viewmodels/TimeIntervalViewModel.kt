package ru.smak.datetimecalc.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.smak.datetimecalc.R
import java.time.Duration
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class TimeIntervalViewModel : ViewModel() {

    private var _inputTime1: LocalTime by mutableStateOf(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))
    private var _inputTime2: LocalTime by mutableStateOf(LocalTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES))
    var inputTime1: LocalTime
        get() = _inputTime1
        set(value){
            _inputTime1 = value.truncatedTo(ChronoUnit.MINUTES)
        }

    var inputTime2: LocalTime
        get() = _inputTime2
        set(value){
            _inputTime2 = value.truncatedTo(ChronoUnit.MINUTES)
        }

    var useHours by mutableStateOf(true)
    var useMinutes by mutableStateOf(true)

    val resultList: List<ResultItem> = listOf(
        ResultItem(::hours, ::useHours, R.string.time_result_in_hours){ useHours = it },
        ResultItem(::minutes, ::useMinutes, R.string.time_result_in_minutes){ useMinutes = it },
    )

    private val iMinutes: Long
        get() {
            val hInM = if (useHours) iHours * 60 else 0
            return if (useMinutes) (Duration.between(inputTime1, inputTime2).toMinutes())
                .let{
                    if (it >= 0) it else (it + 1440L)
                } - hInM
            else 0
        }

    val minutes: String
        get() = if (useMinutes) iMinutes.toString() else "-"

    private val iHours: Long
        get() {
            return if (useHours) (Duration.between(inputTime1, inputTime2).toMinutes())
                .let{
                    if (it >= 0) it else (it + 1440L)
                } / 60
            else 0
        }

    val hours: String
        get() = if (useHours) iHours.toString() else "-"

    fun resetStartTime(): Boolean{
        val changed = _inputTime1.truncatedTo(ChronoUnit.MINUTES).toSecondOfDay() !=
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toSecondOfDay()
        _inputTime1 = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
        return changed
    }

    fun resetFinishTime(): Boolean{
        val changed = _inputTime2.truncatedTo(ChronoUnit.MINUTES).toSecondOfDay() !=
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toSecondOfDay()
        _inputTime2 = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
        return changed
    }

}