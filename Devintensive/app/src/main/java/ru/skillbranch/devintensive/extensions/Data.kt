package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String = "H:mm:ss dd.MM.yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}


fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

enum class TimeUnits(
    private val convertValue: Int,
    private val declentionValues: List<String>
) {
    SECOND(1000, listOf("секунду", "секунды", "секунд")),
    MINUTE(1000 * 60, listOf("минуту", "минуты", "минут")),
    HOUR(1000 * 60 * 60, listOf("час", "часа", "часов")),
    DAY(1000 * 60 * 60 * 24, listOf("день", "дня", "дней"));

    fun toMillis(value: Int) = convertValue * value
    fun getValueFromMillis(value: Long) = value / convertValue

    fun getDeclentedRepresentation(value: Long) =
        "${getValueFromMillis(value)} ${getDeclentedStringValue(getValueFromMillis(value))} назад"

    private fun getDeclentedStringValue(value: Long): String {
        return when (val resultValue = Math.abs(value) % 100) {
            1L -> declentionValues[0]
            in 2..4 -> declentionValues[1]
            0L, in 5..20 -> declentionValues[2]
            else -> getDeclentedStringValue(resultValue % 10)
        }
    }
}