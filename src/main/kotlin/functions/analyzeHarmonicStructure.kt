import math.*
import music.*
import sets.minMaxWithinFactorOf
import sets.subsets
import java.lang.Integer.min

fun analyzeHarmonicStructure(number: Int, maxChordRatio: Float = 1.99F, subsetSize: Int? = null): List<Chord> {
    val factors = factorsOf(number)
    if (factors.any() && hasNoPrimesOver(factors, 7)) {
        val start = subsetSize ?: 2
        val end = subsetSize ?: min(factors.size, 6)
        return (start..end)
            .flatMap { size -> factors.subsets(size) }
            .map { RatioSet(it) }
            .filter { subset ->
                subset.isCoprime()
                        && subset.period() == number
                        && subset.values.minMaxWithinFactorOf(maxChordRatio)
            }.map { Chord(it) }
    }

    return emptyList()
}

fun hasNoPrimesOver(factors: List<Int>, prime: Int): Boolean {
    return factors.none { primes.contains(it) && it > prime }
}
