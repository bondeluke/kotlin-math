package sets

fun Set<Int>.minMaxWithinFactorOf(factor: Float): Boolean {
    val min = this.minOrNull() ?: throw Exception("$this does not have a min element")
    val max = this.maxOrNull() ?: throw Exception("$this does not have a max element")
    return min.toFloat() * factor >= max.toFloat()
}

