package music

import math.RatioSet
import math.divides
import math.primes
import theIntervals
import java.lang.StringBuilder
import kotlin.math.max

data class ChordAndSubChords(val chord: Chord, val subChords: List<Chord>)

data class HarmonicRing(
    val cio: List<Int>, // Canonical Interval Ordering
    val allChords: List<Chord>
) {
    val size = cio.size
    private val intervals = cio.map { theIntervals.first { i -> i.semitones == it }.shortName() }
    private val chordGroups = groupChords(allChords)
    val minimumPeriod = chordGroups.first().maxOf { it.period }

    private val chords = chordGroups.map { ChordAndSubChords(findSuperChord(it), it) }

    private fun findSuperChord(cg: List<Chord>): Chord {
        return cg.reduce { acc, chord -> acc.combine(chord) }
    }

    private fun isValid(chord: ChordAndSubChords): Boolean {
        return chord.subChords.size == size
    }

    private fun warningString(chord: ChordAndSubChords): String {
        return if (isValid(chord)) "" else " !WARNING!"
    }

    override fun toString(): String {
        val chord = chords.first()
        return "$intervals <-> $cio | ${chord.chord.ratioAndPeriod()}${warningString(chord)}"
    }

    fun toStringForCard(): String {
        val chord = chords.first().chord
        return "${chord.period} | ${chord.ratioSet} | ${intervals.joinToString(" + ")} | ${cio.joinToString(" + ")}" +
                warningString(chords.first())
    }

    fun toDetailedString(): String {
        val cg = StringBuilder()

        for (i in chords.indices) {
            val chord = chords[i]
            cg.appendLine("Ratio group ${i + 1} | ${chord.chord.ratioAndPeriod()}${warningString(chord)}")
            cg.appendLine("\t" + chord.subChords.joinToString("\n\t"))
        }

        return "$intervals <-> $cio\n$cg"
    }
}

private fun groupChords(chords: List<Chord>): List<List<Chord>> {
    val bag = chords.toMutableList()

    val groups = mutableListOf<MutableList<Chord>>()

    while (bag.isNotEmpty()) {
        val chord = bag.first()
        bag.remove(chord)

        var found = false
        for (group in groups) {
            val match = group.all { differByOneOrZeroPrimeFactors(chord, it) } &&
                    group.any { haveAllButOneFactorInCommon(chord, it) }

            if (match) {
                group.add(chord)
                found = true
            }
        }

        if (!found) {
            groups.add(mutableListOf(chord))
        }
    }

    return groups
        // Undo the octave redundancy
        .map { it.map { chord -> Chord(RatioSet(chord.ratioSet.values.toList().sorted().dropLast(1).toSet())) } }
        .sortedBy { it.maxOf { g -> g.period } }
}

private fun differByOneOrZeroPrimeFactors(c1: Chord, c2: Chord): Boolean {
    if (c1.period == c2.period) return true

    val large = max(c1.period, c2.period)
    val small = Integer.min(c1.period, c2.period)

    if (!small.divides(large))
        return false

    return primes.contains(large / small)
}

private fun haveAllButOneFactorInCommon(c1: Chord, c2: Chord): Boolean {
    if (c1.size != c2.size) throw Exception("Why aren't these chords the same size")

    return c1.ratioSet.values.intersect(c2.ratioSet.values).size == (c1.size - 1)
}

