package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.Validator
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class EmailValidatorTest(
    value: String,
    expectedResult: Boolean
) : BaseStringValidatorTest(value, expectedResult) {

    override val validator: Validator<String> = EmailValidator()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "input value: \"{0}\"; expected result: {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("test123@mail123.com", true),
                arrayOf("", false),
                arrayOf(" ", false),
                arrayOf("a@b", false),
                arrayOf("a@b.c", true),
                arrayOf("a@b.c.d", true),
                arrayOf("a@b.c.d.e", true),
                arrayOf("123@123.123", true),
                arrayOf("test123.test123@mail123.com", true)
            )
        }
    }
}