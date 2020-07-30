package pl.dzielins42.v8n

import java.util.concurrent.CompletableFuture

/**
 * Interface for validation logic.
 */
interface Validator<T> {
    /**
     * Returns [CompletableFuture] with [Boolean] value stating whether provided `value` is valid
     * or not.
     */
    fun validate(value: T): CompletableFuture<Boolean>
}