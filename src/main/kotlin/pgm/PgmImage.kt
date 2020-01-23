package pgm

import forEachParallel
import kotlinx.coroutines.runBlocking
import java.io.File

open class PgmImage
protected constructor(protected val size: Int) {
    init {
        require(size > 0 && size % 8 == 0) { "Size must be divisible by 8." }
    }

    protected val image = Array(size) { FloatArray(size) }
    protected val blockSize = size / 8

    companion object {
        fun loadFromFile(filename: String): PgmImage {
            require(filename.endsWith(".pgm", ignoreCase = true)) { "File should have .pgm extension." }
            val file = File(filename)
            check(file.exists()) { "File doesn't exist." }

            val values = file.useLines {
                it.flatMap { line ->
                    line.trim().split(' ').asSequence()
                }.toList()
            }
            val size = values[1].toInt()
            val maxValue = values[3].toInt()

            return PgmImage(size).apply { create(values.drop(4), maxValue) }
        }
    }

    private fun create(values: List<String>, maxValue: Int) {
        for (i in 0 until size)
            for (j in 0 until size)
                image[i][j] = values[i * size + j].toFloat() / maxValue
    }

    fun convolute(n: Int) = runBlocking {
        val convolutedImage = Array(size) { FloatArray(size) }
        val blocks = (1 until size - 1).chunked(if (size == 8) 1 else size / 16)
        repeat(n) {
            blocks.forEachParallel {
                for (i in it) {
                    for (j in 1 until size - 1) {
                        var newValue = 0.6f * image[i][j]

                        newValue += 0.1f * image[i - 1][j]
                        newValue += 0.1f * image[i + 1][j]
                        newValue += 0.1f * image[i][j - 1]
                        newValue += 0.1f * image[i][j + 1]

                        convolutedImage[i][j] = newValue
                    }
                }
            }
            convolutedImage[0][0] =
                0.6f * image[0][0] + 0.1f * (image[0][1] + image[1][0])
            convolutedImage[0][size - 1] =
                0.6f * image[0][size - 1] + 0.1f * (image[0][size - 2] + image[1][size - 1])
            convolutedImage[size - 1][0] =
                0.6f * image[size - 1][0] + 0.1f * (image[size - 2][0] + image[size - 1][1])
            convolutedImage[size - 1][size - 1] =
                0.6f * image[size - 1][size - 1] + 0.1f * (image[size - 1][size - 2] + image[size - 2][size - 1])
            (1 until size - 1).forEach { index ->
                convolutedImage[0][index] =
                    0.6f * image[0][index] + 0.1f * (image[0][index - 1] + image[1][index] + image[0][index + 1])
                convolutedImage[index][0] =
                    0.6f * image[index][0] + 0.1f * (image[index - 1][0] + image[index][1] + image[index + 1][0])
                convolutedImage[index][size - 1] =
                    0.6f * image[index][size - 1] + 0.1f * (image[index - 1][size - 1] + image[index][size - 2] + image[index + 1][size - 1])
                convolutedImage[size - 1][index] =
                    0.6f * image[size - 1][index] + 0.1f * (image[size - 1][index - 1] + image[size - 2][index] + image[size - 1][index + 1])
            }
            convolutedImage.copyInto(image)
        }
    }

    fun saveToFile(filename: String, maxValue: Int = 255) {
        require(filename.endsWith(".pgm", ignoreCase = true)) { "File should have .pgm extension." }
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