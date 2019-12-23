package pgm

import java.io.File

open class PgmImage protected constructor(protected val size: Int) {
    init {
        require(size > 0 && size % 8 == 0) { "Size must be divisible by 8." }
    }

    protected val image = Array(size) { DoubleArray(size) }
    protected val blockSize = size / 8

    companion object {
        fun loadFromFile(filename: String, maxValue: Int = 255): PgmImage {
            val file = File(filename)
            check(file.exists()) { "File doesn't exist." }

            val values = file.useLines { lines ->
                lines.flatMap { line ->
                    line.trim().split(' ').asSequence()
                }.toList()
            }
            val size = values[1].toInt()

            return PgmImage(size).apply { create(values.drop(4), maxValue) }
        }
    }

    private fun create(values: List<String>, maxValue: Int) {
        for (i in 0 until size)
            for (j in 0 until size)
                image[i][j] = values[i * size + j].toDouble() / maxValue
    }

    fun convolute(n: Int) {
        val convolutedImage = Array(size) { DoubleArray(size) }
        repeat(n) {
            (0 until size).toList().parallelStream().forEach { i ->
                for (j in 0 until size) {
                    var newValue = 0.6 * image[i][j]

                    if (i != 0) newValue += 0.1 * image[i - 1][j]
                    if (i != size - 1) newValue += 0.1 * image[i + 1][j]
                    if (j != 0) newValue += 0.1 * image[i][j - 1]
                    if (j != size - 1) newValue += 0.1 * image[i][j + 1]

                    convolutedImage[i][j] = newValue
                }
            }
            convolutedImage.copyInto(image)
        }
    }

    fun saveToFile(filename: String, maxValue: Int = 255) {
        val file = File(filename)
        file.createNewFile()

        file.printWriter().use { out ->
            out.println("P2")
            out.println("$size $size")
            out.println(maxValue)

            for (i in 0 until size) {
                for (j in 0 until size) {
                    val value = (image[i][j] * maxValue).toInt()
                    out.print("$value ")
                }
                out.println()
            }
        }
    }
}