package pl.dzielins42.v8n

import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

/**
 * [Validator] implementation that delegates validation logic to external component (eg. REST
 * service).
 *
 * Strategy of handling multiple requests is provided by [Strategy] object.
 */
class DelegatingValidator<T> @JvmOverloads constructor(
    private val strategy: Strategy = BufferStrategy(),
    private val callback: (T) -> Unit
) : Validator<T> {

    override fun validate(value: T): CompletableFuture<Boolean> {
        val completableFuture = CompletableFuture<Boolean>()
        strategy.onNewRequest(completableFuture)
        executor.execute { callback.invoke(value) }
        return completableFuture
    }

    /**
     * Callback method used by external component to post validation result. Behaviour depends on
     * used [Strategy].
     */
    fun onValidationResult(result: Boolean) = strategy.onResult(result)

    /**
     * Interface for component responsible for logic of handling multiple requests.
     */
    interface Strategy {
        fun onNewRequest(completableFuture: CompletableFuture<Boolean>)
        fun onResult(result: Boolean)
    }

    /**
     * [Strategy] implementation that queues validation requests. Requires for external validator to
     * keep order of responses consistent with order of requests.
     */
    class BufferStrategy @JvmOverloads constructor(
        private val limit: Int? = null
    ) : Strategy {

        private val queue: Queue<CompletableFuture<Boolean>> =
            LinkedList<CompletableFuture<Boolean>>()

        override fun onNewRequest(completableFuture: CompletableFuture<Boolean>) {
            if (limit != null) {
                check(queue.size < limit) { "Queue is full" }
            }
            queue.offer(completableFuture)
        }

        override fun onResult(result: Boolean) {
            queue.poll()?.complete(result) ?: kotlin.run {
                IllegalStateException("No validation enqueued")
            }
        }
    }

    /**
     * [Strategy] implementation that enables only one simultaneous validation request. Latest
     * request is used, previous requests are ignored.
     */
    class LatestStrategy : Strategy {

        private var current: CompletableFuture<Boolean>? = null

        override fun onNewRequest(completableFuture: CompletableFuture<Boolean>) {
            current = completableFuture
        }

        override fun onResult(result: Boolean) {
            current?.complete(result)
                ?: kotlin.run { IllegalStateException("No validation enqueued") }
            current = null
        }
    }

    /**
     * [Strategy] implementation that enables only one simultaneous validation request. First
     * request is used. If new validation is requested before previous ends, IllegalStateException
     * is thrown.
     */
    class SingleStrategy : Strategy {

        private var current: CompletableFuture<Boolean>? = null

        override fun onNewRequest(completableFuture: CompletableFuture<Boolean>) {
            if (current != null) {
                throw IllegalStateException("Validation in progress")
            }
            current = completableFuture
        }

        override fun onResult(result: Boolean) {
            current?.complete(result)
                ?: kotlin.run { IllegalStateException("No validation enqueued") }
            current = null
        }
    }

    companion object {
        private val executor = Executors.newCachedThreadPool()
    }
}