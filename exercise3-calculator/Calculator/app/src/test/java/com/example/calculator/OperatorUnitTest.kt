package com.example.calculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import kotlin.random.Random

class OperatorUnitTest {

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
        // 4 + 2 = 6
        assertEquals(BigDecimal("6"), Operator.ADD.getOperation()(BigDecimal("4"), BigDecimal("2")))

        // 4 - 2 = 2
        assertEquals(
            BigDecimal("2"),
            Operator.SUBTRACT.getOperation()(BigDecimal("4"), BigDecimal("2"))
        )

        // 4 * 2 = 8
        assertEquals(
            BigDecimal("8"),
            Operator.MULTIPLY.getOperation()(BigDecimal("4"), BigDecimal("2"))
        )

        // 4 / 2 = 2
        assertEquals(
            BigDecimal("2"),
            Operator.DIVIDE.getOperation()(BigDecimal("4"), BigDecimal("2"))
        )

        // 4 mod 2 = 0
        assertEquals(
            BigDecimal("0"),
            Operator.MODULO.getOperation()(BigDecimal("4"), BigDecimal("2"))
        )
    }

    private val random = Random.Default
    private fun randomBigDecimal(from: Double, to: Double): BigDecimal {
        return random.nextDouble(from, to).toBigDecimal()
    }

    @Test
    fun testGetOperationRandomized() {
        val numberOfTests = 100

        for (i in 1..numberOfTests) {
            val num1 = randomBigDecimal(-100.0, 100.0)
            val num2 = randomBigDecimal(-100.0, 100.0)
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