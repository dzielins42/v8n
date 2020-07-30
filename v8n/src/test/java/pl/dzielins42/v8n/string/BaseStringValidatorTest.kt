package pl.dzielins42.v8n.string

import pl.dzielins42.v8n.Validator
import org.junit.Assert
import org.junit.Test

abstract class BaseStringValidatorTest(
    private val value: String,
    private val expectedResult: Boolean
) {

    abstract val validator: Validator<String>

    @Test
    fun `Given value, result is correct`() {
        // Arrange
        // Act
        val result = validator.validate(value).get()
        // Assert
        Assert.assertEquals(expectedResult, result)
    }
}