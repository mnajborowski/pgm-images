package pgm

class VerticalStripes(size: Int) : PgmImage(size) {
    init {
        require(size % 8 == 0) { "Size must be divisible by 8." }
        create()
    }

    private fun create() {
        for (i in 0 until size)
            for (j in 0 until size)
                if (j % (2 * blockSize) < blockSize)
                    image[i][j] = 1.0f
    }
}