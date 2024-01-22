package ru.smak.datetimecalc.ui

import ru.smak.datetimecalc.R
import ru.smak.datetimecalc.viewmodels.ViewPage

fun getPageTitleId(viewPage: ViewPage) = when (viewPage) {
    ViewPage.DATE_INTERVAL -> R.string.date_interval_title
    ViewPage.DATE_ADD -> R.string.date_add_title
    ViewPage.TIME -> R.string.time_interval_title
}

fun getNavTitleId(viewPage: ViewPage) = when (viewPage) {
    ViewPage.DATE_INTERVAL -> R.string.date_interval_nav
    ViewPage.DATE_ADD -> R.string.date_add_nav
    ViewPage.TIME -> R.string.time_interval_nav
}

fun getNavIconTint(viewPage: ViewPage) = when(viewPage){
    ViewPage.DATE_INTERVAL,
    ViewPage.TIME -> R.color.interval
    ViewPage.DATE_ADD -> R.color.add
}

fun getNavIconId(viewPage: ViewPage) = when (viewPage) {
    ViewPage.DATE_INTERVAL,
    ViewPage.DATE_ADD -> R.drawable.twotone_date_range_24
    ViewPage.TIME -> R.drawable.twotone_access_time_24
}
