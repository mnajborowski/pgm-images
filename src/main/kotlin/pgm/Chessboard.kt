package pgm

class Chessboard(size: Int) : PgmImage(size) {
    init {
        create()
    }

    override fun create() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (((i / blockSize) + (j / blockSize)) % 2 == 0)
                    image[i][j] = 1.0
            }
        }
    }
}