import math.*
import music.*
import sets.subsets

// Chords + Scales in every key
data class NoteSet(val elements: Set<Int>, val order: Int) {
    override fun toString(): String {
        return "{${elements.joinToString()}}"
    }

    fun getShape(): Shape {
        if (elements.isEmpty()) return Shape(emptyList(), order)

        val sorted = elements.toList().sorted()
        return (0 until sorted.size - 1).map {
            sorted[it + 1] - sorted[it]
        }.plus(order + sorted.first() - sorted.last())
            .let { Shape(canonicalRingOrdering(it), order) }
    }
}


// All chord and scale shapes
data class Shape(val elements: List<Int>, val order: Int) {
    override fun toString(): String {
        return "(${elements.joinToString()})"
    }

    fun mirror(): Shape {
        return Shape(canonicalRingOrdering(elements.reversed()), order)
    }
}

fun main() {
    println("seed -> binary -> bracelets -> mirrorlet")

    for (seed in 3..12) {
        val noteSets = seed.subsets()
            .map { NoteSet(it, seed) }
            .sortedBy { it.elements.size }

        val shapes = noteSets.groupBy { it.getShape() }

        val groups = shapes.map { it.key }
            .groupBy { it.elements.size }.entries
            .map { Pair(it.key, groupsOfMirrorPairs(it.value).sortedBy { g -> g.size }) }
            .toMap()

//        val x = groups.entries.drop(groups.size / 2).sumBy { it.value.size }

        println("$seed -> ${noteSets.size} -> ${shapes.size} -> ${groups.entries.sumBy { it.value.size }}")
//        for (group in groups) {
//            println("${group.key} -> ${group.value.size}")
//            for (set in group.value) {
//                if (set.size == 1)
//                    println("${set.first()}")
//                else
//                    println(set.joinToString(" <---> "))
//            }
//        }
    }
}


fun groupsOfMirrorPairs(shapes: List<Shape>): List<List<Shape>> {
    val bag = shapes.toMutableList()

    val groups = mutableListOf<MutableList<Shape>>()

    while (bag.isNotEmpty()) {
        val shape = bag.first()
        bag.remove(shape)

        var found = false
        for (group in groups) {
            val match = group.any { it == shape.mirror() }

            if (match) {
                group.add(shape)
                found = true
            }
        }

        if (!found) {
            groups.add(mutableListOf(shape))
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