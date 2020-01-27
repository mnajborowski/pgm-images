package pgm

import java.io.File

open class PgmImage
protected constructor(val size: Int) {
    init {
        require(size > 0 && size % 8 == 0) { "Size must be divisible by 8." }
    }

    val image = Array(size) { FloatArray(size) }
    protected val blockSize = size / 8

    fun convolute(n: Int, filter: Filter) = filter.convolute(n, this)

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
                    val value = (image[i][j] * maxValue).toInt().coerceIn(0, maxValue)
                    out.print("$value ")
                }
                out.println()
            }
        }
    }

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

            return PgmImage(size).apply {
                val pixels = values.drop(4)
                for (i in 0 until size)
                    for (j in 0 until size)
                        image[i][j] = pixels[i * size + j].toFloat() / maxValue
            }
        }
    }
}