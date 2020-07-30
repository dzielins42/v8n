package pl.dzielins42.v8n.string

import androidx.core.util.PatternsCompat

/**
 * String validator that matches email pattern.
 *
 * @see [PatternsCompat.EMAIL_ADDRESS]
 */
class EmailValidator : PatternValidator(PatternsCompat.EMAIL_ADDRESS)