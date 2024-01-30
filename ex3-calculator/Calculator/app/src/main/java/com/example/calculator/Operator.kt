package com.example.calculator

import java.math.BigDecimal

enum class Operator {
    ADD {
        override fun getSymbol(): String = "+"
        override fun getOperation(): (BigDecimal, BigDecimal) -> BigDecimal = { x, y -> x + y }
    },
    SUBTRACT {
        override fun getSymbol(): String = "-"
        override fun getOperation(): (BigDecimal, BigDecimal) -> BigDecimal = { x, y -> x - y }
    },
    MULTIPLY {
        override fun getSymbol(): String = "*"
        override fun getOperation(): (BigDecimal, BigDecimal) -> BigDecimal = { x, y -> x * y }
    },
    DIVIDE {
        override fun getSymbol(): String = "/"
        override fun getOperation(): (BigDecimal, BigDecimal) -> BigDecimal = { x, y -> x / y }
    },
    MODULO {
        override fun getSymbol(): String = "%"
        override fun getOperation(): (BigDecimal, BigDecimal) -> BigDecimal = { x, y -> x % y }
    };

    abstract fun getSymbol(): String
    abstract fun getOperation(): (BigDecimal, BigDecimal) -> BigDecimal
}