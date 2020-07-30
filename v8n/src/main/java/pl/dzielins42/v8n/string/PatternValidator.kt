package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.SyncValidator
import java.util.regex.Pattern

open class PatternValidator(
    private val pattern: Pattern
) : SyncValidator<String>(
    { value -> pattern.matcher(value).matches() }
) {
    constructor(pattern: String) : this(Pattern.compile(pattern))
}