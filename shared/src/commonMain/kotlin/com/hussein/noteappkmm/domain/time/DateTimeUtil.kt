package com.hussein.noteappkmm.domain.time

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {
    fun now () :LocalDateTime{
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
    fun toEpochMillis(dateTime: LocalDateTime) : Long{
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
    fun formatNoteDate(dateTime: LocalDateTime): String{
        // This show datetime like Jan 7 2024, 19:30
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() } //Jan
        val day= if(dateTime.dayOfMonth < 10) "0${dateTime.dayOfMonth}" else dateTime.dayOfMonth //06
        val year = dateTime.year //2024
        val hour = if(dateTime.hour < 10) "0${dateTime.hour}" else dateTime.hour //19
        val minute = if(dateTime.minute < 10) "0${dateTime.minute}" else dateTime.minute //30

        return buildString {
            append(month)
            append(" ")
            append(day)
            append(" ")
            append(year)
            append(", ")
            append(hour)
            append(":")
            append(minute)
        }

    }
}