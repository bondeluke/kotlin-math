package sets

fun <T> Iterable<T>.subsets(subsetSize: Int): Set<Set<T>> {
    val list = toList()
    return emptyList<Int>()
        .buildIndexSubsets(subsetSize, list.size)
        .map { it.map { index -> list[index] }.toSet() }
        .toSet()
}

private fun List<Int>.buildIndexSubsets(subsetSize: Int, setSize: Int): List<List<Int>> {
    if (size == subsetSize)
        return listOf(this)

    val start = if (this.isEmpty()) 0 else this.last() + 1
    val end = setSize - subsetSize + size
    return (start..end).flatMap { index ->
        this.plus(index).buildIndexSubsets(subsetSize, setSize)
    }
}

fun <T> Iterable<T>.subsets(): Set<Set<T>> {
    val set = this.toList()
    if (set.size > 16) error("Too many.")
    return allBinarySequences(set.size).map { binary ->
        (set.indices)
            .filter { index -> binary[index] }
            .map { index -> set[index] }
            .toSet()
    }.toSet()
}

fun allBinarySequences(size: Int): List<List<Boolean>> {
    return if (size == 1) listOf(listOf(true), listOf(false))
    else allBinarySequences(size - 1).let { result ->
        result.map { listOf(true) + it } + result.map { listOf(false) + it }
    }
}

fun Int.subsets(): Set<Set<Int>> {
    return (0 until this).subsets()
}
