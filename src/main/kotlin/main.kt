import functions.*
import math.*
import sets.subsets

fun main() {
    println("seed -> elements -> necklaces -> bracelets")

    for (seed in 6..12) {
        val elements = seed.subsets()
            .map { Element(it, seed) }
            .sortedBy { it.set.size }

        val necklaces = elements.groupBy { it.getNecklace() }

        val bracelets = necklaces.map { it.key }
            .groupBy { it.elements.size }.entries
            .map { Pair(it.key, groupsOfMirrorPairs(it.value).sortedBy { g -> g.size }) }
            .toMap()

        println("$seed -> ${elements.size} -> ${necklaces.size} -> ${bracelets.entries.sumBy { it.value.size }}")
    }
}


fun groupsOfMirrorPairs(necklaces: List<Necklace>): List<List<Necklace>> {
    val bag = necklaces.toMutableList()

    val groups = mutableListOf<MutableList<Necklace>>()

    while (bag.isNotEmpty()) {
        val necklace = bag.first()
        bag.remove(necklace)

        var found = false
        for (group in groups) {
            val match = group.any { it == necklace.mirror() }

            if (match) {
                group.add(necklace)
                found = true
            }
        }

        if (!found) {
            groups.add(mutableListOf(necklace))
        }
    }

    return groups
}

fun research() {
    printIntervals()
    val skipTheseNumbers = getIntervalsToSkip().map { it.ratio.period() }
    analyzeNumbersAsIntervalPatterns()
    val chords = (1..180)
        .filterNot { skipTheseNumbers.any { n -> n.divides(it) } }
        .flatMap {
            analyzeHarmonicStructure(it, maxChordRatio = 2F)
        }

    findRings(chords)
    analyzeRatioStructures(chords)
    analyzeRedundancy(chords)
}