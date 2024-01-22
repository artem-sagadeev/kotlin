package ru.smak.datetimecalc.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class DateAddViewModel : ViewModel() {

    private var _inputDate: LocalDate by mutableStateOf(LocalDate.now())
    var inputDate: LocalDate
        get() = _inputDate
        set(value) {
            _inputDate = value
        }

    fun resetInput(): Boolean {
        val changed = _inputDate.toEpochDay() != LocalDate.now().toEpochDay()
        _inputDate = LocalDate.now()
        return changed
    }

    var days: Long by mutableLongStateOf(0L)
    var weeks: Long by mutableLongStateOf(0L)
    var months: Long by mutableLongStateOf(0L)
    var years: Long by mutableLongStateOf(0L)

    var isAddAction: Boolean by mutableStateOf(true)

    val resultDate: LocalDate
        get() = if (isAddAction)
            LocalDate
                .from(inputDate)
                .plusYears(years)
                .plusMonths(months)
                .plusWeeks(weeks)
                .plusDays(days)
        else
            LocalDate
                .from(inputDate)
                .minusYears(years)
                .minusMonths(months)
                .minusWeeks(weeks)
                .minusDays(days)
}