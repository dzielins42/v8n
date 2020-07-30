package pl.dzielins42.v8n.string

/**
 * String validator that matches alphanumerical characters.
 */
class AlphanumericValidator : PatternValidator(PATTERN) {
    companion object {
        private const val PATTERN = "^[a-zA-Z0-9]*\$"
    }
}