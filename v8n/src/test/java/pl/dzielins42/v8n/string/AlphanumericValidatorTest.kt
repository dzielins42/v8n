package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.Validator
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class AlphanumericValidatorTest(
    value: String,
    expectedResult: Boolean
) : BaseStringValidatorTest(value, expectedResult) {

    override val validator: Validator<String> = AlphanumericValidator()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "input value: \"{0}\"; expected result: {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("123", true),
                arrayOf("abc", true),
                arrayOf("ABC", true),
                arrayOf("4Bc", true),
                arrayOf("abc123", true),
                arrayOf("abc 123", false),
                arrayOf("abc-123", false)
            )
        }
    }
}