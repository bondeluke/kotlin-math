package math

fun factorsOf(number: Int): List<Int> {
    return (2 until number)
        .filter {
            number % it == 0
        }
}

fun primeFactorization(number: Int): List<Int> {
    if (primes.contains(number)) {
        return listOf(number)
    }

    val factors = mutableListOf<Int>()
    var n = number
    for (p in primes) {
        while (p.divides(n) && n != 1) {
            factors.add(p)
            n /= p
        }
    }
    return factors
}