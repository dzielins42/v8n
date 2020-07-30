package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.SyncValidator

/**
 * String validator that matches any string that is not empty. If optional parameter
 * `allowWhitespaceCharacters` is true, blank strings are not matched.
 */
class NonemptyValidator @JvmOverloads constructor(
    allowWhitespaceCharacters: Boolean = false
) : SyncValidator<String>({ value ->
    if (allowWhitespaceCharacters) {
        value.isNotEmpty()
    } else {
        value.isNotBlank()
    }
})