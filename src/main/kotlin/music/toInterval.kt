package music

import math.RatioSet
import theIntervals
import java.lang.Exception

fun RatioSet.toInterval(): Interval {
    try {
        return theIntervals.single { this.reduce() == it.ratio }
    } catch (e: NoSuchElementException) {
        throw RatioNotSupportedException(this)
    }
}

class RatioNotSupportedException(ratio: RatioSet) : Exception("The ratio $ratio is not supported.")