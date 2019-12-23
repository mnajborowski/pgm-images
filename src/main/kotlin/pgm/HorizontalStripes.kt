package pgm

class HorizontalStripes(size: Int) : PgmImage(size) {
    init {
        create()
    }

    private fun create() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i % (2 * blockSize) < blockSize) image[i][j] = 1.0
            }
        }
    }
}