package org.fierg.extensions

import kotlin.math.log10


fun Int?.length() = when(this) {
    null -> 0
    0 -> 1
    else -> log10(kotlin.math.abs(toDouble())).toInt() + 1
}

fun Int.toDigits(): Array<Int> {
    var temp: Int = this
    val list = mutableListOf<Int>()
    do {
        list.add(temp % 10)
        temp /= 10
    } while (temp > 0)
    return list.toTypedArray()
}