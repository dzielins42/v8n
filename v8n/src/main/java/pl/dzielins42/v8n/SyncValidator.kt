package pl.dzielins42.v8n

import java.util.concurrent.CompletableFuture

/**
 * [Validator] implementation that synchronously executes `validationFunction` to validate provided
 * value.
 */
open class SyncValidator<T>(
    private val validationFunction: (T) -> Boolean
) : Validator<T> {

    override fun validate(value: T): CompletableFuture<Boolean> {
        return CompletableFuture.completedFuture(
            validationFunction.invoke(value)
        )
    }
}