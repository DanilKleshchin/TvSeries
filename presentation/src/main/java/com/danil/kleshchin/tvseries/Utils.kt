package com.danil.kleshchin.tvseries

import androidx.core.text.HtmlCompat

fun String.fromHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}

fun Double.roundToTwoLastDigits(): Double {
    return String.format("%.2f", this).toDouble()
}
