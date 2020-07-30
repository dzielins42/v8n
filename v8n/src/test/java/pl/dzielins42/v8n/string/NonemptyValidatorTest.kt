package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.Validator
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class NonemptyValidatorTest(
    value: String,
    expectedResult: Boolean,
    allowWhitespaceCharacters: Boolean
) : BaseStringValidatorTest(value, expectedResult) {

    override val validator: Validator<String> = NonemptyValidator(allowWhitespaceCharacters)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "input value: \"{0}\"; expected result: {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("test", true, false),
                arrayOf("", false, false),
                arrayOf(" ", false, false),
                arrayOf("test", true, true),
                arrayOf("", false, true),
                arrayOf(" ", true, true)
            )
        }
    }
}