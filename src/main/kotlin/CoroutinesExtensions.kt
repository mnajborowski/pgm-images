import kotlinx.coroutines.*

suspend inline fun <T> Collection<T>.forEachParallel(crossinline action: suspend (T) -> Unit) = withContext(Dispatchers.Default) {
    map { async(Dispatchers.Default) { action(it) } }.forEach { it.await() }
}