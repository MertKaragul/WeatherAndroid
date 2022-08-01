package com.frag.weather.Util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object UtilObj {
    private val dateFormat = LocalDate.now()
    val API_KEY = ""
    val getCurrentDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(dateFormat)
}