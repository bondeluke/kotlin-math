data class Element(val set: Set<Int>, val order: Int) {
    override fun toString(): String {
        return "{${set.joinToString()}}"
    }

    fun getNecklace(): Necklace {
        if (set.isEmpty()) return Necklace(emptyList(), order)

        val sorted = set.toList().sorted()
        return (0 until sorted.size - 1).map {
            sorted[it + 1] - sorted[it]
        }.plus(order + sorted.first() - sorted.last())
            .let { Necklace(canonicalNecklaceOrdering(it), order) }
    }
}