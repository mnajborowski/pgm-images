package pgm

object Filters {
    val blur = Filter(
        arrayOf(
            floatArrayOf(0.0f, 0.1f, 0.0f),
            floatArrayOf(0.1f, 0.6f, 0.1f),
            floatArrayOf(0.0f, 0.1f, 0.0f)
        )
    )

    val weird = Filter(
        arrayOf(
            floatArrayOf(0.0f, 0.1f, 0.0f),
            floatArrayOf(0.1f, 2f, 0.1f),
            floatArrayOf(0.0f, 0.1f, 0.0f)
        )
    )
}