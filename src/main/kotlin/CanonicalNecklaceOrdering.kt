import kotlin.math.pow

fun canonicalNecklaceOrdering(intervalSet: List<Int>): List<Int> {
    return (intervalSet.indices).map {
        intervalSet.takeLast(intervalSet.size - it) + intervalSet.take(it)
    }.maxByOrNull {
        it.foldIndexed(1F, { index, acc, value -> 12F.pow(intervalSet.size - index - 1) * value + acc })
    } ?: throw Exception("The interval set $intervalSet did not have a maximum.")
}