package ru.smak.datetimecalc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.smak.datetimecalc.R
import ru.smak.datetimecalc.viewmodels.DateIntervalViewModel
import ru.smak.datetimecalc.viewmodels.ResultItem
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.reflect.KProperty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    items: Map<Int, String>,
    onItemClick: (Int)->Unit,
    modifier: Modifier = Modifier,
    currentItemIndex: Int = 0,
){
    var expanded by remember{ mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded,
        onExpandedChange = {expanded = !expanded},
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = items[currentItemIndex] ?: items.values.first(),
            onValueChange = {},
            modifier = Modifier
                .defaultMinSize(minWidth = 48.dp)
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            for (item in items){
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item.value,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    onClick = {
                        expanded = false
                        onItemClick(item.key)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun MenuPreview(){
    Menu(items = mapOf(0 to "Item1", 1 to "Item2"), {})
}

@Composable
fun CheckedText(
    text: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    showCheckBox: Boolean = true,
    onCheckedChanged: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            modifier = Modifier.alpha(if (showCheckBox) 1f else 0f),
            enabled = showCheckBox,
            onCheckedChange = { onCheckedChanged(!checked) }
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    if (showCheckBox)
                        onCheckedChanged(!checked)
                },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(textAlign = TextAlign.Center),
        )
    }
}

@Preview(locale = "ru")
@Composable
fun CheckedTextPreview(){
    CheckedText(checked = true, text = stringResource(id = R.string.date_result_in_days, "-"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    state: DatePickerState,
    title: String = "",
    onDismissRequest: ()->Unit = {},
    onConfirm: ()->Unit = {},
){
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            FilledTonalButton(onClick = onConfirm) {
                Icon(
                    painterResource(id = R.drawable.twotone_event_available_24),
                    contentDescription = stringResource(R.string.dialog_btn_ok)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Выбрать",
                )
            }
        },
    ) {
        DatePicker(
            state = state,
            modifier = Modifier.padding(top = 8.dp),
            title = {
                Text(
                    text = title,
                    modifier = Modifier.padding(start = 25.dp, top = 16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            },
            showModeToggle = false
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(locale = "ru")
@Composable
fun DateDialogPreview(){
    DateDialog(
        state = DatePickerState(null, null, 1700..2499, DisplayMode.Picker),
        title = stringResource(id = R.string.date_picker1_title)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialog(
    state: TimePickerState,
    title: String = "",
    onDismissRequest: ()->Unit = {},
    onConfirm: ()->Unit = {},
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min)
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(5)
                )
        ) {
            Column(
                Modifier
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(start = 25.dp, top = 16.dp, bottom = 25.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                TimePicker(
                    state = state,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 25.dp),
                )
                FilledTonalButton(
                    onClick = onConfirm,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(
                        painterResource(id = R.drawable.twotone_more_time_24),
                        contentDescription = stringResource(R.string.dialog_btn_ok)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Выбрать",
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(locale = "ru")
@Composable
fun TimeDialogPreview(){
    TimeDialog(
        state = TimePickerState(15, 37, true),
        title = stringResource(id = R.string.date_picker1_title)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    date: LocalDate = LocalDate.now(),
    title: String = "",
    clickable: Boolean = true,
    showTodayButton: Boolean = true,
    yearRange: IntRange = 1700..2499,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    background: Color = MaterialTheme.colorScheme.surface,
    onDateReset: ()->Unit = {},
    onDateChanged: (LocalDate)->Unit = {},
){
    var showDialog by remember {
        mutableStateOf(false)
    }
    val dateString = buildString {
        append(DateTimeFormatter
            .ofLocalizedDate(FormatStyle.FULL)
            .withLocale(Locale.getDefault())
            .format(date)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
        if (date.isBefore(LocalDate.of(1, 1, 1))) {
            append(" ")
            append(stringResource(id = R.string.bc_appendix))
        }
    }
    val pickerState = remember{
        DatePickerState(
            date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000L,
            date.atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000L,
            yearRange,
            DisplayMode.Picker
        )
    }
    if (showDialog) {
        DateDialog(
            state = pickerState,
            title = title,
            onDismissRequest = { showDialog = false },
            onConfirm = {
                showDialog = false
                onDateChanged(
                    LocalDateTime.ofEpochSecond(pickerState.selectedDateMillis?.div(1000L) ?: 0L, 0, ZoneOffset.UTC).toLocalDate()
                )
            }
        )
    }
    ElevatedCard(
        modifier = if (clickable) modifier.clickable { showDialog = true }
                   else modifier,
        colors = CardDefaults.elevatedCardColors(
            contentColor = contentColor,
            containerColor = background,
        )
    ) {
        if (title.isNotBlank()) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = dateString,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 18.dp),
                fontSize = 18.sp,
            )
            if (showTodayButton) {
                FilledIconButton(
                    onClick = onDateReset,
                    modifier = Modifier
                        .padding(8.dp)
                        .minimumInteractiveComponentSize(),
                ) {
                    Icon(
                        painterResource(id = R.drawable.twotone_today_24),
                        contentDescription = stringResource(R.string.today),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DateSelectorPreview(){
    DateSelector(title = "Заголовок селектора", modifier = Modifier.fillMaxWidth())
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelector(
    modifier: Modifier = Modifier,
    time: LocalTime = LocalTime.now(),
    title: String = "",
    clickable: Boolean = true,
    showNowButton: Boolean = true,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    background: Color = MaterialTheme.colorScheme.surface,
    onTimeReset: ()->Unit = {},
    onTimeChanged: (LocalTime)->Unit = {},
){
    var showDialog by remember {
        mutableStateOf(false)
    }

    val timeString = DateTimeFormatter
        .ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .format(time)

    val pickerState = remember{
        TimePickerState(time.hour, time.minute, true)
    }
    if (showDialog) {
        TimeDialog(
            state = pickerState,
            title = title,
            onDismissRequest = { showDialog = false },
            onConfirm = {
                showDialog = false
                onTimeChanged(
                    LocalTime.of(pickerState.hour, pickerState.minute)
                )
            }
        )
    }
    ElevatedCard(
        modifier = if (clickable) modifier.clickable { showDialog = true }
        else modifier,
        colors = CardDefaults.elevatedCardColors(
            contentColor = contentColor,
            containerColor = background,
        )
    ) {
        if (title.isNotBlank()) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = timeString,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 18.dp),
                fontSize = 32.sp,
            )
            if (showNowButton) {
                FilledIconButton(
                    onClick = onTimeReset,
                    modifier = Modifier
                        .padding(8.dp)
                        .minimumInteractiveComponentSize(),
                ) {
                    Icon(
                        painterResource(id = R.drawable.twotone_access_time_24),
                        contentDescription = stringResource(R.string.today),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TimeSelectorPreview(){
    TimeSelector(
        title = "Заголовок селектора",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun AddSubtractSwitcher(
    addSubtractAction: Boolean,
    modifier: Modifier = Modifier,
    onActionChanged: (Boolean)-> Unit = {},
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        Text(
            text = stringResource(R.string.subtractAction),
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    onActionChanged(false)
                },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(textAlign = TextAlign.Center)
        )
        Switch(
            checked = addSubtractAction,
            onCheckedChange = {
                onActionChanged(!addSubtractAction)
            },
            Modifier.padding(4.dp),
            colors = SwitchDefaults.colors(
                uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.background,
                uncheckedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedIconColor = MaterialTheme.colorScheme.background,
                checkedBorderColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.background,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedIconColor = MaterialTheme.colorScheme.background,
            )
        )
        Text(
            text = stringResource(R.string.addAction),
            modifier = Modifier
                .padding(4.dp)
                .clickable {
                    onActionChanged(true)
                },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(textAlign = TextAlign.Center)
        )
    }
}

@Preview
@Composable
fun AddSubtractSwitcherPreview(){
    var value by remember { mutableStateOf(true) }
    AddSubtractSwitcher(
        value,
        Modifier.fillMaxWidth(),
        onActionChanged = {value = it}
    )
}

@Composable
fun OutlinedUIntField(
    value: String,
    modifier: Modifier = Modifier,
    label: String = "",
    onValueChanged: (String)->Unit = {},
){
    OutlinedTextField(
        value = value,
        onValueChange = {
            val v = it.toShortOrNull()
            if (it.isBlank() || (v != null && v >= 0)){
                onValueChanged(v?.toString() ?: "")
            }
        },
        modifier = modifier.defaultMinSize(minWidth = 30.dp),
        singleLine = true,
        label = {
            Text(
                text = label,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview
@Composable
fun OutlinedIntFieldPreview(){
    var v by remember { mutableStateOf("123") }
    OutlinedUIntField(value = v, onValueChanged = { v = it })
}

@Composable
fun IntervalResults(
    items: List<ResultItem>,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    background: Color = MaterialTheme.colorScheme.surface,
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = background,
            contentColor = contentColor,
        ),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            for (i in 0..< items.size / 2 + items.size % 2) {
                if (i > 0){
                    Divider(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (j in 0..1) {
                        items.getOrNull(2 * i + j)?.let { item ->
                            if (j > 0)
                                Divider(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight(),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                )
                            CheckedText(
                                text = stringResource(
                                    id = item.labelResourceId,
                                    item.result.call()
                                ),
                                checked = item.checked.call(),
                                modifier = Modifier.weight(1f),
                                showCheckBox = (2 * i + j + 1) != items.size,
                                onCheckedChanged = item.onClick
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(locale = "ru")
@Composable
fun IntervalResultsPreview(){
    val Item = object{
        val d = "15"
        val m = "2"
        val y = "-"
        val t = true
    }
    IntervalResults(
        listOf(
            ResultItem(Item::d, Item::t, R.string.date_result_in_days) {},
            ResultItem(Item::m, Item::t, R.string.date_result_in_months) {},
            ResultItem(Item::y, Item::t, R.string.date_result_in_years) {},
        ),
        Modifier.fillMaxWidth()
    )
}