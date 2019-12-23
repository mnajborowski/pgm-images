fun main() {
    val chessboard = Chessboard(128)
    chessboard.convolute(100)
    chessboard.saveFile("chessboard.pgm", 255)

    val tLetter = TLetter(64)
    tLetter.saveFile("tLetter.pgm", 255)

    val verticalStripes = VerticalStripes(64)
    verticalStripes.saveFile("verticalStripes.pgm", 255)

    val horizontalStripes = HorizontalStripes(64)
    horizontalStripes.saveFile("horizontalStripes.pgm", 255)
}