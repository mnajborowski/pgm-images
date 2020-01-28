package pgm

import utils.toGrayscaleImage
import java.io.File
import javax.imageio.ImageIO

open class PgmImage
protected constructor(val size: Int) {
    init {
        require(size > 0) { "Size must be > 0." }
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
        fun loadFromPgmFile(filename: String): PgmImage {
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

        fun loadFromImage(filename: String): PgmImage {
            require(filename.endsWith(".jpg", ignoreCase = true) || filename.endsWith(".png", ignoreCase = true)) {
                "Image should have .jpg or .png extension."
            }
            val file = File(filename)
            check(file.exists()) { "File doesn't exist." }

            val inputImage = ImageIO.read(file).toGrayscaleImage()
            require(inputImage.height == inputImage.width) { "Input image should have equal width and height." }
            val imageSize = inputImage.height

            return PgmImage(imageSize).apply {
                val imageRaster = inputImage.raster
                for (i in 0 until inputImage.height)
                    for (j in 0 until inputImage.width)
                        image[i][j] = imageRaster.getSampleFloat(j, i, 0) / 255.0f
            }
        }
    }
}