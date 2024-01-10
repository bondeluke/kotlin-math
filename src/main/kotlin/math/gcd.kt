package math

fun gcd(a: Int, b: Int): Int {
    if (a == 0) return b
    return gcd(b % a, a)
}

fun gcd(set: Set<Int>): Int {
    val list = set.toList()
    var d = list[0]
    for (i in 1 until list.size) {
        d = gcd(d, list[i])
    }
    return d
}
