import pgm.*
import kotlin.system.measureTimeMillis

fun main() {
    val chessboard = Chessboard(1024)
    val time = measureTimeMillis { chessboard.convolute(1000) }
    println(time)
    chessboard.saveToFile("chessboard.pgm", 255)

    val tLetter = TLetter(64)
    tLetter.saveToFile("tLetter.pgm", 255)

    val verticalStripes = VerticalStripes(64)
    verticalStripes.saveToFile("verticalStripes.pgm", 255)

    val horizontalStripes = HorizontalStripes(64)
    horizontalStripes.saveToFile("horizontalStripes.pgm", 255)

    val image = PgmImage.loadFromFile("chessboard.pgm")
    image.saveToFile("newChessboard.pgm", 255)
}