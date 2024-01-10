package math

fun RatioSet.isCoprime(): Boolean {
    // 6:10:15 cannot be reduced, despite any 2 numbers sharing a factor
    return gcd(this.values) == 1
}
