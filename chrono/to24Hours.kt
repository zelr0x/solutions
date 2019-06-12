/**
 * Converts time in 12-hour format into 24-hour format.
 * 07:05:45PM -> 19:05:45
 * @param s a string representation of time in format hh:mm:ssa 
 * @return a string representation of time HH:mm:ss
 */
fun to24Hours(s: String): String {
    val hours = s.take(2)
    val isAM = s.endsWith("AM", true)
    val is12 = hours == "12"
    val hours24 = if (is12 && isAM) {
        "00"
    } else if (!is12 && !isAM) {
        (hours.toInt() + 12).toString()
    } else {
        hours
    }
    return hours24 + s.substring(2, 8)
}
