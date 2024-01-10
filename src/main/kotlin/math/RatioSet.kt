package math

data class RatioSet(val values: Set<Int>) {
    fun reduce(): RatioSet {
        val gcd = gcd(values)
        return RatioSet(values.map { it / gcd }.toSet())
    }

    fun period(): Int {
        return lcm(values)
    }

    override fun toString(): String {
        return values.joinToString(":")
    }
}