package math

import kotlin.math.pow

private fun Int.pow(exp: Int): Int {
    return this.toFloat().pow(exp).toInt()
}