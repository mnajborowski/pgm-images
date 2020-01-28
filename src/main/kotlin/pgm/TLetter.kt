package pgm

class TLetter(size: Int) : PgmImage(size) {
    init {
        require(size % 8 == 0) { "Size must be divisible by 8." }
        create()
    }

    private fun create() {
        for (i in 0 until size)
            for (j in 0 until size)
                when {
                    i < blockSize -> image[i][j] = 1.0f
                    j in ((size / 2 - blockSize / 2) until (size / 2 + blockSize / 2)) -> image[i][j] = 1.0f
                }
    }
}