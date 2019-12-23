package pgm

class TLetter(size: Int) : PgmImage(size) {
    init {
        create()
    }

    override fun create() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                when {
                    i < blockSize -> image[i][j] = 1.0
                    j in ((size / 2 - blockSize / 2) until (size / 2 + blockSize / 2)) -> image[i][j] = 1.0
                }
            }
        }
    }
}