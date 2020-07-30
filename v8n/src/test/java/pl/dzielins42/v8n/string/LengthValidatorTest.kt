package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.Validator
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class LengthValidatorTest(
    value: String,
    expectedResult: Boolean,
    minLength: Int?,
    maxLength: Int?
) : BaseStringValidatorTest(value, expectedResult) {

    override val validator: Validator<String> by lazy {
        if (minLength != null && maxLength != null) {
            LengthValidator(
                minLength = minLength,
                maxLength = maxLength
            )
        } else if (minLength != null) {
            LengthValidator(
                minLength = minLength
            )
        } else if (maxLength != null) {
            LengthValidator(
                maxLength = maxLength
            )
        } else {
            LengthValidator()
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "input value: \"{0}\"; expected result: {1}")
        fun data(): Collection<Array<out Any?>> {
            return listOf(
                arrayOf("", true, null, null),
                arrayOf(" ", true, null, null),
                arrayOf("abc", true, null, null),
                arrayOf("", false, 1, null),
                arrayOf(" ", true, 1, null),
                arrayOf("abc", true, 1, null),
                arrayOf("", true, null, 2),
                arrayOf(" ", true, null, 2),
                arrayOf("abc", false, null, 2),
                arrayOf("", false, 1, 2),
                arrayOf(" ", true, 1, 2),
                arrayOf("abc", false, 1, 2)
            )
        }
    }
}