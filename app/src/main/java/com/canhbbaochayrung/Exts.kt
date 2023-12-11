package com.canhbbaochayrung

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toString(pattern: String): String{
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

