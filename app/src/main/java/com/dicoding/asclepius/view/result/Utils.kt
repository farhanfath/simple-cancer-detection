package com.dicoding.asclepius.view.result

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun String.dateFormat(): String {
    val inputDateFormat = SimpleDateFormat(INPUT_FORMAT)
    inputDateFormat.timeZone = TimeZone.getTimeZone(INPUT_TIME_ZONE)
    val formatDate = inputDateFormat.parse(this) as Date
    val outputDateFormat = SimpleDateFormat(OUTPUT_FORMAT)
    outputDateFormat.timeZone = TimeZone.getDefault()
    return outputDateFormat.format(formatDate)
}

const val INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
const val OUTPUT_FORMAT = "dd-MM-yyyy"
const val INPUT_TIME_ZONE = "UTC"

