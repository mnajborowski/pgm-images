package pgm

import kotlinx.coroutines.runBlocking

class Filter(private val kernel: Array<FloatArray>) {
    init {
        require(kernel.size == 3 && kernel.all { row -> row.size == 3 }) { "The kernel must be 3x3 size." }
    }

    fun convolute(n: Int, pgmImage: PgmImage) = runBlocking {
        val image = pgmImage.image // Reference to original array
        val size = pgmImage.size

        val convolutedImage = Array(size) { FloatArray(size) }

        repeat(n) {
            var upperLeftCorner = kernel[1][1] * image[0][0]
            upperLeftCorner += kernel[2][1] * image[1][0]
            upperLeftCorner += kernel[1][2] * image[0][1]
            upperLeftCorner += kernel[2][2] * image[1][1]
            convolutedImage[0][0] = upperLeftCorner

            var upperRightCorner = kernel[1][1] * image[0][size - 1]
            upperRightCorner += kernel[1][0] * image[0][size - 2]
            upperRightCorner += kernel[2][1] * image[1][size - 1]
            upperRightCorner += kernel[1][1] * image[1][size - 2]
            convolutedImage[0][size - 1] = upperRightCorner

            var lowerLeftCorner = kernel[1][1] * image[size - 1][0]
            lowerLeftCorner += kernel[0][1] * image[size - 2][0]
            lowerLeftCorner += kernel[1][2] * image[size - 1][1]
            lowerLeftCorner += kernel[0][2] * image[size - 2][1]
            convolutedImage[size - 1][0] = lowerLeftCorner

            var lowerRightCorner = kernel[1][1] * image[size - 1][size - 1]
            lowerRightCorner += kernel[1][0] * image[size - 1][size - 2]
            lowerRightCorner += kernel[0][1] * image[size - 2][size - 1]
            lowerRightCorner += kernel[0][0] * image[size - 2][size - 2]
            convolutedImage[size - 1][size - 1] = lowerRightCorner

            for (index in 1 until size - 1) {
                var upperRowValue = kernel[1][1] * image[0][index]
                upperRowValue += kernel[1][0] * image[0][index - 1]
                upperRowValue += kernel[1][2] * image[0][index + 1]
                upperRowValue += kernel[2][0] * image[1][index - 1]
                upperRowValue += kernel[2][1] * image[1][index]
                upperRowValue += kernel[2][2] * image[1][index + 1]
                convolutedImage[0][index] = upperRowValue

                var lowerRowValue = kernel[1][1] * image[size - 1][index]
                lowerRowValue += kernel[1][0] * image[size - 1][index - 1]
                lowerRowValue += kernel[1][2] * image[size - 1][index + 1]
                lowerRowValue += kernel[0][0] * image[size - 2][index - 1]
                lowerRowValue += kernel[0][1] * image[size - 2][index]
                lowerRowValue += kernel[0][2] * image[size - 2][index + 1]
                convolutedImage[size - 1][index] = lowerRowValue

                var leftColumnValue = kernel[1][1] * image[index][0]
                leftColumnValue += kernel[0][1] * image[index - 1][0]
                leftColumnValue += kernel[2][1] * image[index + 1][0]
                leftColumnValue += kernel[0][2] * image[index + 1][1]
                leftColumnValue += kernel[1][2] * image[index][1]
                leftColumnValue += kernel[2][2] * image[index - 1][1]
                convolutedImage[index][0] = leftColumnValue

                var rightColumnValue = kernel[1][1] * image[index][size - 1]
                rightColumnValue += kernel[0][1] * image[index - 1][size - 1]
                rightColumnValue += kernel[2][1] * image[index + 1][size - 1]
                rightColumnValue += kernel[0][0] * image[index - 1][size - 2]
                rightColumnValue += kernel[1][0] * image[index][size - 2]
                rightColumnValue += kernel[2][0] * image[index + 1][size - 2]
                convolutedImage[index][size - 1] = rightColumnValue
            }

            for (i in 1 until size - 1) {
                for (j in 1 until size - 1) {
                    var newValue = kernel[1][1] * image[i][j]
                    newValue += kernel[0][0] * image[i + 1][j - 1]
                    newValue += kernel[0][1] * image[i + 1][j]
                    newValue += kernel[0][2] * image[i + 1][j + 1]
                    newValue += kernel[1][0] * image[i][j - 1]
                    newValue += kernel[1][2] * image[i][j + 1]
                    newValue += kernel[2][0] * image[i - 1][j - 1]
                    newValue += kernel[2][1] * image[i - 1][j]
                    newValue += kernel[2][2] * image[i - 1][j + 1]

                    convolutedImage[i][j] = newValue
                }
            }

            convolutedImage.copyInto(image)
        }
    }

    override fun toString(): String = kernel.contentDeepToString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Filter

        if (!kernel.contentDeepEquals(other.kernel)) return false

        return true
    }

    override fun hashCode(): Int {
        return kernel.contentDeepHashCode()
    }
}