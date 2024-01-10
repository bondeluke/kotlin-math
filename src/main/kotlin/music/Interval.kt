package music

import math.RatioSet
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.roundToInt

val semitoneValue = 2.0.pow(1.0 / 12.0)

data class Interval(val ratio: RatioSet, val semitones: Int) {
    private val lower = ratio.values.minOrNull() ?: throw Exception("${ratio.values} does not have a min value.")
    private val upper = ratio.values.maxOrNull() ?: throw Exception("${ratio.values} does not have a max value.")

    fun shortName(): String {
        return when (semitones) {
            23 -> "OM7"
            22 -> "Om7"
            21 -> "OM6"
            20 -> "Om6"
            19 -> "OP5"
            18 -> "OT"
            17 -> "OP4"
            16 -> "OM3"
            15 -> "Om3"
            14 -> "M9"
            13 -> "m9"
            12 -> "O"
            11 -> "M7"
            10 -> "m7"
            9 -> "M6"
            8 -> "m6"
            7 -> "P5"
            6 -> "T"
            5 -> "P4"
            4 -> "M3"
            3 -> "m3"
            2 -> "M2"
            1 -> "m2"
            else -> ""
        }
    }

    val error = (upper.toDouble()/ lower.toDouble()) / (semitoneValue.pow(semitones))
    val centsError = (log2(error) * 1200).roundToInt()

    override fun toString(): String {
        return "$ratio ($semitones semitones ${if (centsError > 0) "+" else ""}${centsError} cents) ${shortName()}"
    }
}