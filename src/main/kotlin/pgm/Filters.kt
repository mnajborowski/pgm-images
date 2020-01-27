package pgm

object Filters {
    val blur = Filter(
        arrayOf(
            floatArrayOf(0.0f, 0.1f, 0.0f),
            floatArrayOf(0.1f, 0.6f, 0.1f),
            floatArrayOf(0.0f, 0.1f, 0.0f)
        )
    )

    val boxBlur = Filter(
        arrayOf(
            floatArrayOf(1.0f / 9, 1.0f / 9, 1.0f / 9),
            floatArrayOf(1.0f / 9, 1.0f / 9, 1.0f / 9),
            floatArrayOf(1.0f / 9, 1.0f / 9, 1.0f / 9)
        )
    )

    val weird = Filter(
        arrayOf(
            floatArrayOf(0.0f, 0.1f, 0.0f),
            floatArrayOf(0.1f, 2.0f, 0.1f),
            floatArrayOf(0.0f, 0.1f, 0.0f)
        )
    )

    val outline = Filter(
        arrayOf(
            floatArrayOf(-1.0f, -1.0f, -1.0f),
            floatArrayOf(-1.0f, 8.0f, -1.0f),
            floatArrayOf(-1.0f, -1.0f, -1.0f)
        )
    )

    val sharpen = Filter(
        arrayOf(
            floatArrayOf(0.0f, -1.0f, 0.0f),
            floatArrayOf(-1.0f, 5.0f, -1.0f),
            floatArrayOf(0.0f, -1.0f, 0.0f)
        )
    )

    val emboss = Filter(
        arrayOf(
            floatArrayOf(-2.0f, -1.0f, 0.0f),
            floatArrayOf(-1.0f, 1.0f, 1.0f),
            floatArrayOf(0.0f, 1.0f, 2.0f)
        )
    )

    val bottomSobel = Filter(
        arrayOf(
            floatArrayOf(-1.0f, -2.0f, -1.0f),
            floatArrayOf(0.0f, 0.0f, 0.0f),
            floatArrayOf(1.0f, 2.0f, 1.0f)
        )
    )
}