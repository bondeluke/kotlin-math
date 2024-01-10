package math

fun Int.removeFactorsOf(prime: Int): Int {
    var tmp = this
    while (tmp % prime == 0) {
        tmp /= prime
    }
    return tmp
}