package music

import math.RatioSet
import math.factorsOf
import math.primeFactorization
import sets.consecutiveSum
import sets.getConsecutiveSubsets

class Chord(val ratioSet: RatioSet) {
    val intervals: List<Interval> = getConsecutiveSubsets(ratioSet.values.toList(), 2)
        .map { pair -> RatioSet(pair.toSet()).toInterval() }

    val size = ratioSet.values.size
    val period = ratioSet.period()
    val notes = getNotes(consecutiveSum(intervals.map { it.semitones }))
    private val intervalString = intervals.joinToString(" + ") { it.shortName() }

    override fun toString(): String {
        return "$period | (${primeFactorization(period).joinToString()}) | $ratioSet | $intervalString" +
                " | ${notes.joinToString { it.value.toString() }}" +
                " | ${notes.joinToString { it.name }}"
    }

    fun ratioAndPeriod(): String {
        return "$ratioSet ($period)"
    }

    fun combine(chord: Chord): Chord {
        return Chord(RatioSet((this.ratioSet.values + chord.ratioSet.values)))
    }
}