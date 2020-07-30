package pl.dzielins42.v8n

import java.util.concurrent.CompletableFuture

/**
 * [Validator] implementation that composes multiple [Validator] objects. Each element of the
 * composition has to positively validate the value.
 */
class ConjunctionValidator<T>(
    private vararg val elements: Validator<T>
) : Validator<T> {

    override fun validate(value: T): CompletableFuture<Boolean> {
        return elements.fold(
            CompletableFuture.completedFuture(true),
            { acc, i ->
                acc.thenCompose {
                    if (it) {
                        i.validate(value)
                    } else {
                        CompletableFuture.completedFuture(false)
                    }
                }
            }
        )
    }
}