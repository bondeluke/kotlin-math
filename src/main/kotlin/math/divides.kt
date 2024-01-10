package math

fun Int.divides(number: Int): Boolean {
    return number % this == 0
}