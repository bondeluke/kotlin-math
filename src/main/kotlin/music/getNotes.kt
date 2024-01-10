package music

import java.lang.Exception

fun getNoteName(value: Int): String {
    return when (value % 12) {
        0 -> "C"
        1 -> "Db"
        2 -> "D"
        3 -> "Eb"
        4 -> "E"
        5 -> "F"
        6 -> "Gb"
        7 -> "G"
        8 -> "Ab"
        9 -> "A"
        10 -> "Bb"
        11 -> "B"
        else -> throw Exception("Cannot find note name for value $value")
    }
}


fun getNotes(values: List<Int>): List<Note> {
    return values.map { Note(it, getNoteName(it)) }
}