package sets

// 1 2 3 4 -> (1 2) (1 3) (1 4) (2 3) (2 4) (3 4)
fun <T> getPairs(list: List<T>): List<Pair<T, T>> {
    val pairs = mutableListOf<Pair<T, T>>()

    for (i1 in 0 until list.size - 1) {
        for (i2 in i1 + 1 until list.size) {
            pairs.add(Pair(list[i1], list[i2]))
        }
    }

    return pairs
}