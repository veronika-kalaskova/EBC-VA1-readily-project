package com.example.readily.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateUtils {

    companion object {

        private val DATE_FORMAT_CS = "dd. MM. yyyy"
        private val DATE_FORMAT_EN = "yyyy/MM/dd"

        fun getDateString(date: Long): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date

            val simpleDateFormat: SimpleDateFormat
            if (LanguageUtils.isLanguageCloseToCzech()){
                simpleDateFormat = SimpleDateFormat(DATE_FORMAT_CS, Locale.GERMAN)
            } else {
                simpleDateFormat = SimpleDateFormat(DATE_FORMAT_EN, Locale.ENGLISH)
            }
            return simpleDateFormat.format(calendar.time)
        }
    }
}
