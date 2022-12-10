package Extensions

import kotlin.math.log10

fun Int?.length() = when(this) {
    null -> 0
    0 -> 1
    else -> log10(kotlin.math.abs(toDouble())).toInt() + 1
}