package com.example.calculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import kotlin.random.Random

class OperatorUnitTest {

    private val random = Random.Default
    private fun randomBigDecimal(from: Double, to: Double): BigDecimal {
        return random.nextDouble(from, to).toBigDecimal()
    }

    @Test
    fun testGetSymbol() {
        assertEquals("+", Operator.ADD.getSymbol())
        assertEquals("-", Operator.SUBTRACT.getSymbol())
        assertEquals("*", Operator.MULTIPLY.getSymbol())
        assertEquals("/", Operator.DIVIDE.getSymbol())
        assertEquals("%", Operator.MODULO.getSymbol())
    }

    @Test
    fun testGetOperation() {
        assertEquals(
            BigDecimal("1111111110111110"),
            Operator.ADD.getOperation()(
                BigDecimal("123456789012345"),
                BigDecimal("987654321098765")
            )
        )

        assertEquals(
            BigDecimal("864197532086420"),
            Operator.SUBTRACT.getOperation()(
                BigDecimal("987654321098765"),
                BigDecimal("123456789012345")
            )
        )

        assertEquals(
            BigDecimal("121932631137021071359549253925"),
            Operator.MULTIPLY.getOperation()(
                BigDecimal("123456789012345"),
                BigDecimal("987654321098765")
            )
        )

        assertEquals(
            BigDecimal("197530864219753"),
            Operator.DIVIDE.getOperation()(
                BigDecimal("987654321098765"),
                BigDecimal("5")
            )
        )

        assertEquals(
            BigDecimal("9000005"),
            Operator.MODULO.getOperation()(
                BigDecimal("987654321098765"),
                BigDecimal("123456789012345")
            )
        )
    }

    @Test
    fun testGetOperationRandomized() {
        val numberOfTests = 1000

        for (i in 1..numberOfTests) {
            val num1 = randomBigDecimal(-1000000.0, 1000000.0)
            val num2 = randomBigDecimal(-1000000.0, 1000000.0)
            // protect against division by 0
            val divNum2 = if (num2 == BigDecimal(0)) BigDecimal(1) else num2

            val calculatedAdd = Operator.ADD.getOperation()(num1, num2)
            val calculatedSubtract = Operator.SUBTRACT.getOperation()(num1, num2)
            val calculatedMultiply = Operator.MULTIPLY.getOperation()(num1, num2)
            val calculatedDivide = Operator.DIVIDE.getOperation()(num1, divNum2)
            val calculatedModulo = Operator.MODULO.getOperation()(num1, divNum2)

            val expectedAdd = num1 + num2
            val expectedSubtract = num1 - num2
            val expectedMultiply = num1 * num2
            val expectedDivide = num1 / divNum2
            val expectedModulo = num1 % divNum2

            assertEquals(calculatedAdd, expectedAdd)
            assertEquals(calculatedSubtract, expectedSubtract)
            assertEquals(calculatedMultiply, expectedMultiply)
            assertEquals(calculatedDivide, expectedDivide)
            assertEquals(calculatedModulo, expectedModulo)
        }
    }

}