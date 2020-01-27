import pgm.*
import kotlin.system.measureTimeMillis

fun main() {
    // Shapes
    val chessboard = Chessboard(1024)
    chessboard.saveToFile("chessboard.pgm", 255)

    val tLetter = TLetter(64)
    tLetter.saveToFile("t_letter.pgm", 255)

    val verticalStripes = VerticalStripes(64)
    verticalStripes.saveToFile("vertical_stripes.pgm", 255)

    val horizontalStripes = HorizontalStripes(64)
    horizontalStripes.saveToFile("horizontal_stripes.pgm", 255)

    // Filters
    val blur = PgmImage.loadFromFile("chessboard.pgm")
    val time = measureTimeMillis { blur.convolute(1000, Filters.blur) }
    println(time)
    blur.saveToFile("chessboard_blur.pgm", 255)

    val boxBlur = PgmImage.loadFromFile("chessboard.pgm")
    boxBlur.convolute(20, Filters.boxBlur)
    boxBlur.saveToFile("chessboard_box_blur.pgm", 255)

    val sharpen = PgmImage.loadFromFile("chessboard_blur.pgm")
    sharpen.convolute(1, Filters.sharpen)
    sharpen.saveToFile("chessboard_sharpen.pgm", 255)

    val weird = PgmImage.loadFromFile("chessboard_blur.pgm")
    weird.convolute(2, Filters.weird)
    weird.saveToFile("chessboard_weird.pgm", 255)

    val outline = PgmImage.loadFromFile("chessboard.pgm")
    outline.convolute(1, Filters.outline)
    outline.saveToFile("chessboard_outline.pgm", 255)

    val emboss = PgmImage.loadFromFile("chessboard_blur.pgm")
    emboss.convolute(1, Filters.emboss)
    emboss.saveToFile("chessboard_emboss.pgm", 255)

    val bottomSobel = PgmImage.loadFromFile("chessboard.pgm")
    bottomSobel.convolute(1, Filters.bottomSobel)
    bottomSobel.saveToFile("chessboard_bottom_sobel.pgm", 255)
}