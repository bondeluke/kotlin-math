data class Necklace(val elements: List<Int>, val order: Int) {
    override fun toString(): String {
        return "(${elements.joinToString()})"
    }

    fun mirror(): Necklace {
        return Necklace(canonicalNecklaceOrdering(elements.reversed()), order)
    }
}