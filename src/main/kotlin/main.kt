import pgm.Filters
import pgm.PgmImage
import kotlin.system.measureTimeMillis

fun main() {
    // Filters
    val blur = PgmImage.loadFromFile("chessboard.pgm")
    val time = measureTimeMillis { blur.convolute(1000, Filters.blur) }
    println(time)
    blur.saveToFile("chessboard_blur.pgm", 255)
}