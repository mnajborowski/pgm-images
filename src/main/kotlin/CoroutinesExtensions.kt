import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

inline fun <T> Collection<T>.forEachParallel(crossinline action: suspend (T) -> Unit) = runBlocking {
    map { async(Dispatchers.Default) { action(it) } }.forEach { it.await() }
}