package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.SyncValidator

/**
 * String validator that matches strings with length in given length range.
 *
 * 0 is default value for `minLength`. [Int.MAX_VALUE] is default value for `maxLength`
 */
class LengthValidator @JvmOverloads constructor(
    minLength: Int = 0,
    maxLength: Int = Int.MAX_VALUE
) : SyncValidator<String>(
    { value -> value.length in minLength..maxLength }
)