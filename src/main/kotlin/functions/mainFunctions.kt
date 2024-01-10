import math.RatioSet
import math.factorsOf
import music.*

fun printIntervals() {
    print(theHealthyIntervals.groupBy { it.shortName() }.map { it.value.count() }.reduce { x, y -> x * y })
    println(" total combinations")
    for (intervals in theHealthyIntervals.groupBy { it.shortName() }) {
        println("${intervals.key} (${intervals.value.count()})")
        for (interval in intervals.value.sortedBy { it.ratio.period() }) {
            println("\t" + interval)
        }
    }
}

fun findRings(chords: List<Chord>) {
    chords.filter { it.notes.last().value == 12 } // Introduce octave redundancy
        .groupBy { canonicalRingOrdering(it.intervals.map { i -> i.semitones }) }
        .map { HarmonicRing(it.key, it.value) }
        .sortedBy { it.minimumPeriod }
        .groupBy { it.size }
        .filter { it.key != 1 }
        .forEach {
            println("*********** ${it.key}-NOTE RINGS (${it.value.size}) ***********")
            for (ring in it.value.sortedBy { r -> r.minimumPeriod }) {
                println(ring)
            }
        }
}

fun analyzeRatioStructures(chords: List<Chord>) {
    val groups = chords.groupBy { it.ratioSet.period() }

    for (group in groups) {
        println("${group.key} (${factorsOf(group.key).joinToString()})") //
        for (chord in group.value) {
            println("\t$chord")
        }
    }

}

fun analyzeRedundancy(chords: List<Chord>) {
    val noteGroups = chords.groupBy { it.notes.joinToString { n -> n.name } }.toList()
        .sortedByDescending { it.second.size }

    for (noteGroup in noteGroups) {
        println(noteGroup.first)
        for (chord in noteGroup.second.sortedBy { it.ratioSet.period() }) {
            println("\t${chord.ratioSet.period()} -> ${chord.ratioSet}")
        }
    }
}

fun analyzeNumbersAsIntervalPatterns() {
    (1..360)
        .map { Pair(it, factorsOf(it)) }
        .filter { it.second.size >= 3 && hasNoPrimesOver(it.second, 7) && it.second.anyRatioGapIsLessThan(2F) }
        .forEach { i ->
            val intervals = i.second.getRatios()
            println("${i.first} -> ${i.second}\n\t${intervals.map { it.shortName() }}")
        }
}


fun List<Int>.anyRatioGapIsLessThan(gapSize: Float): Boolean {
    for (index in 0 until this.size - 1) {
        if (this[index + 1] / this[index] < gapSize)
            return true
    }
    return false
}

fun List<Int>.getRatios(): List<Interval> {
    return (0 until this.size - 1).map {
        RatioSet(setOf(this[it], this[it + 1])).toInterval()
    }
}
