package utils

import kotlinx.coroutines.*
import java.awt.image.BufferedImage

suspend inline fun <T> Collection<T>.forEachParallel(crossinline action: suspend (T) -> Unit) = coroutineScope {
    map { async(Dispatchers.Default) { action(it) } }.forEach { it.await() }
}

fun BufferedImage.toGrayscaleImage(): BufferedImage {
    val grayscaleImage = BufferedImage(
        this.width,
        this.height,
        BufferedImage.TYPE_BYTE_GRAY
    )

    grayscaleImage.createGraphics().run {
        drawImage(this@toGrayscaleImage, 0, 0, null)
        dispose()
    }

    return grayscaleImage
}