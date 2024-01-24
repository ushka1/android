package com.example.calculator

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

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
        assertEquals(BigDecimal("2"), Operator.SUBTRACT.getOperation()(BigDecimal("4"), BigDecimal("2")))
        // 4 * 2 = 8
        assertEquals(BigDecimal("8"), Operator.MULTIPLY.getOperation()(BigDecimal("4"), BigDecimal("2")))
        // 4 / 2 = 2
        assertEquals(BigDecimal("2"), Operator.DIVIDE.getOperation()(BigDecimal("4"), BigDecimal("2")))
        // 4 mod 2 = 0
        assertEquals(BigDecimal("0"), Operator.MODULO.getOperation()(BigDecimal("4"), BigDecimal("2")))
    }

}