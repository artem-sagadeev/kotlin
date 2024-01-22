package ru.smak.datetimecalc.viewmodels

import kotlin.reflect.KProperty

data class ResultItem(
    var result: KProperty<String>,
    var checked: KProperty<Boolean>,
    val labelResourceId: Int,
    val onClick: (Boolean)->Unit,
)