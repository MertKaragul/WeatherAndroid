package com.frag.weather.Util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object UtilObj {
    private val dateFormat = LocalDate.now()
    val API_KEY = "3c7c7fa5da2ec76d599bae0fca61d1af"
    val getCurrentDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(dateFormat)
}