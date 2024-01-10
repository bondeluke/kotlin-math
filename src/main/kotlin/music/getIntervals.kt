import math.RatioSet
import math.divides
import math.primeFactorization
import music.Interval
import music.OvertoneTemperament
import sets.getPairs
import kotlin.math.abs

fun getOvertoneTemperaments(limit: Int, base: Int = 12): List<OvertoneTemperament> {
    val primeOvertones = listOf(
        OvertoneTemperament(2, base),
        OvertoneTemperament(3, 19),
        OvertoneTemperament(5, 28),
        OvertoneTemperament(7, 34),
        OvertoneTemperament(11, 42),
        OvertoneTemperament(13, 45),
        OvertoneTemperament(17, 49),
        OvertoneTemperament(19, 51),
//        OvertoneTemperament(23, 55),
//        OvertoneTemperament(29, 55),
    )

    return (2..limit)
        .map { Pair(it, primeFactorization(it)) }
        .filter { hasNoPrimesOver(it.second, primeOvertones.maxOf { po -> po.overtone }) }
        .map { pair ->
            val semitoneSum =
                pair.second.map { prime -> primeOvertones.single { ot -> ot.overtone == prime }.semitones }.sum()
            OvertoneTemperament(pair.first, semitoneSum)
        }
}

fun getIntervals(): List<Interval> {
    return getPairs(getOvertoneTemperaments(64))
        .map {
            Interval(
                RatioSet(setOf(it.first.overtone, it.second.overtone)).reduce(),
                it.second.semitones - it.first.semitones
            )
        }
        .distinct()
        .sortedWith(compareBy({ it.semitones }, { it.centsError }))
        .toList()
}

val theIntervals = getIntervals()
val theHealthyIntervals =
    theIntervals.filter { it.semitones in 1..11 && abs(it.centsError) <= 33 && it.ratio.period() <= 16 * 15 }

fun getIntervalsToSkip(): List<Interval> {
    return theIntervals.filter {
        abs(it.centsError) >= 50 ||
                it.semitones == 0 ||
                (12).divides(it.semitones) && it.centsError != 0
    }
}