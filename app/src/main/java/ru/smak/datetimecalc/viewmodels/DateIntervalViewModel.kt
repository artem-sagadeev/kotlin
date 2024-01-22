package ru.smak.datetimecalc.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.smak.datetimecalc.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class DateIntervalViewModel : ViewModel(){

    private var _firstDate: LocalDate by mutableStateOf(LocalDate.now())
    var firstDate: LocalDate
        get() = _firstDate
        set(value) {
            _firstDate = value
        }

    private var _secondDate: LocalDate by mutableStateOf(LocalDate.now().plusDays(1))
    var secondDate: LocalDate
        get() = _secondDate
        set(value) {
            _secondDate = value
        }

    fun resetDate1(): Boolean {
        val changed = _firstDate.toEpochDay() != LocalDate.now().toEpochDay()
        _firstDate = LocalDate.now()
        return changed
    }

    fun resetDate2(): Boolean {
        val changed = _secondDate.toEpochDay() != LocalDate.now().toEpochDay()
        _secondDate = LocalDate.now()
        return changed
    }

    private val minDate: LocalDate
        get() = if (firstDate <= secondDate) firstDate else secondDate

    private val maxDate: LocalDate
        get() = if (firstDate >  secondDate) firstDate else secondDate


    var useYears by mutableStateOf(true)
    var useMonths by mutableStateOf(true)
    var useWeeks by mutableStateOf(false)
    var useDays by mutableStateOf(true)

    val resultList: List<ResultItem> = listOf(
        ResultItem(::years, ::useYears, R.string.date_result_in_years,){ useYears = it },
        ResultItem(::months, ::useMonths, R.string.date_result_in_months,){ useMonths = it },
        ResultItem(::weeks, ::useWeeks, R.string.date_result_in_weeks,){ useWeeks = it },
        ResultItem(::days, ::useDays, R.string.date_result_in_days,){ useDays = it },
    )

    private val iDays: Long
        get() {
            val y = if (useYears) iYears else 0L
            val m = if (useMonths) iMonths else 0L
            val w = if (useWeeks) iWeeks else 0L
            return if (useDays) ChronoUnit.DAYS.between(
                minDate,
                maxDate.minusYears(y).minusMonths(m).minusWeeks(w)
            ).absoluteValue
            else 0
        }

    val days: String
        get() = if (useDays) iDays.toString() else "-"

    private val iWeeks: Long
        get() {
            val y = if (useYears) iYears else 0L
            val m = if (useMonths) iMonths else 0L
            return if (useWeeks) ChronoUnit.WEEKS.between(
                minDate,
                maxDate.minusYears(y).minusMonths(m)
            ).absoluteValue
            else 0
        }

    val weeks: String
        get() = if (useWeeks) iWeeks.toString() else "-"

    private val iMonths: Long
        get() {
            val y = if (useYears) iYears else 0L
            return if (useMonths) ChronoUnit.MONTHS.between(
                minDate,
                maxDate.minusYears(y)
            ).absoluteValue
            else 0
        }

    val months: String
        get() = if (useMonths) iMonths.toString() else "-"

    private val iYears: Long
        get() = if (useYears) ChronoUnit.YEARS.between(minDate, maxDate).absoluteValue
                else 0

    val years: String
        get() = if (useYears) iYears.toString() else "-"
}
