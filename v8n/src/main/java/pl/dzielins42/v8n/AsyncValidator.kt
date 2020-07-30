package pl.dzielins42.v8n

import java.util.concurrent.CompletableFuture

/**
 * [Validator] implementation that asynchronously executes `validationFunction` to validate provided
 * value.
 */
open class AsyncValidator<T>(
    private val validationFunction: (T) -> Boolean
) : Validator<T> {

    override fun validate(value: T): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync {
            validationFunction.invoke(value)
        }
    }
}