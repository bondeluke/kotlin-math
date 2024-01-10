package sets

fun getConsecutiveSubsets(numbers: List<Int>, size: Int): List<List<Int>> {
    return (0..numbers.size - size)
        .map {
            numbers.drop(it).take(size)
        }
}
