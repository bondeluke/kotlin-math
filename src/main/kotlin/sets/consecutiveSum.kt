package sets

fun consecutiveSum(list: List<Int>): List<Int> {
    return (0..list.size).map { list.take(it).sum() }
}