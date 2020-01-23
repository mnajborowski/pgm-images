import pgm.*
import kotlin.system.measureTimeMillis

fun main() {
    val chessboard = Chessboard(1024)
    val time = measureTimeMillis { chessboard.convolute(1000, Filters.blur) }
    println(time)
    chessboard.saveToFile("chessboard.pgm", 255)

    val tLetter = TLetter(64)
    tLetter.saveToFile("t_letter.pgm", 255)

    val verticalStripes = VerticalStripes(64)
    verticalStripes.saveToFile("vertical_stripes.pgm", 255)

    val horizontalStripes = HorizontalStripes(64)
    horizontalStripes.saveToFile("horizontal_stripes.pgm", 255)

    val image = PgmImage.loadFromFile("chessboard.pgm")
    image.convolute(2, Filters.weird)
    image.saveToFile("chessboard_copy.pgm", 255)
}