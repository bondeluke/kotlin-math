package math

fun lcm(a: Int, b: Int): Int {
    return (a * b) / gcd(a, b)
}

fun lcm(set: Set<Int>): Int {
    val list = set.toList()
    var l = list[0]
    for (i in 1 until list.size) {
        l = lcm(l, list[i])
    }
    return l
}
