package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import java.math.BigDecimal
import kotlin.math.ln

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

class MainActivity : AppCompatActivity() {
    private var result: String? = null
    private var operator: Operator? = null
    private var operand: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setButtonListeners()
    }

    private fun setError(message: String) {
        val errorTextView = findViewById<TextView>(R.id.error)
        errorTextView.text = message
    }

    private fun appendDigit(digit: String) {
        if (operator == null) {
            if (result == null || result == "0") {
                result = digit
            } else {
                result += digit
            }
        } else {
            if (operand == null || operand == "0") {
                operand = digit
            } else {
                operand += digit
            }
        }

        render()
    }

    private fun appendDot() {
        if (operator == null) {
            if (result == null) {
                result = "0."
            } else if (!result!!.contains(".")) {
                result += "."
            }
        } else {
            if (operand == null) {
                operand = "0."
            } else if (!operand!!.contains(".")) {
                operand += "."
            }
        }

        render()
    }

    private fun setOperator(operator: Operator) {
        if (this.result == null) return

        this.operator = operator
        render()
    }

    private fun calculate() {
        if (result != null && operator != null && operand != null) {
            try {
                val num1 = result!!.toBigDecimal()
                val num2 = operand!!.toBigDecimal()
                val res = operator!!.getOperation()(num1, num2)

                result = res.toPlainString()
                operator = null
                operand = null

                setError("")
            } catch (e: Exception) {
                result = null
                operator = null
                operand = null

                setError("ERROR")
            }
        }
        render()
    }

    private fun changeSign() {
        if (operator == null) {
            if (result == null) return

            result = if (result!![0] == '-') {
                result!!.substring(1)
            } else {
                "-$result"
            }
        } else {
            if (operand == null) return

            operand = if (operand!![0] == '-') {
                operand!!.substring(1)
            } else {
                "-$operand"
            }
        }

        render()
    }

    private fun power() {
        if (result != null && operator == null && operand == null) {
            result = (BigDecimal(result) * BigDecimal(result)).toPlainString()
        } else {
            setError("INVALID INPUT")
        }

        render()
    }

    private fun logarithm() {
        if (result != null && operator == null && operand == null) {
            result = ln(BigDecimal(result).toDouble()).toString()
            if (result == "NaN") {
                result = null
                setError("ERROR")
            } else {
                setError("")
            }
        } else {
            setError("INVALID INPUT")
        }

        render()
    }


    private fun backspace() {
        if (operand != null) {
            operand = operand!!.substring(0, operand!!.length - 1)

            if (operand!!.isEmpty()) {
                operand = null
            }
        } else if (operator != null) {
            operator = null
        } else if (result != null) {
            result = result!!.substring(0, result!!.length - 1)

            if (result!!.isEmpty()) {
                result = null
            }
        }

        render()
    }

    private fun clear() {
        result = null
        operator = null
        operand = null
        setError("")

        render()
    }

    private fun render() {
        var text = ""

        if (result != null) {
            text += result.toString()
        }
        if (operator != null) {
            text += " " + operator!!.getSymbol()
        }
        if (operand != null) {
            text += " " + operand.toString()
        }

        val resultTextView: TextView = findViewById(R.id.result)
        resultTextView.text = text
    }

    private fun setButtonListeners() {
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("button$i", "id", packageName)
            val button = findViewById<Button>(buttonId)

            button?.setOnClickListener {
                appendDigit(i.toString())
            }
        }

        val dotButton = findViewById<Button>(R.id.buttonDot)
        dotButton?.setOnClickListener {
            appendDot()
        }

        val addButton = findViewById<Button>(R.id.buttonAdd)
        addButton?.setOnClickListener {
            setOperator(Operator.ADD)
        }

        val subtractButton = findViewById<Button>(R.id.buttonSubtract)
        subtractButton?.setOnClickListener {
            setOperator(Operator.SUBTRACT)
        }

        val multiplyButton = findViewById<Button>(R.id.buttonMultiply)
        multiplyButton?.setOnClickListener {
            setOperator(Operator.MULTIPLY)
        }

        val divideButton = findViewById<Button>(R.id.buttonDivide)
        divideButton?.setOnClickListener {
            setOperator(Operator.DIVIDE)
        }

        val moduloButton = findViewById<Button>(R.id.buttonModulo)
        moduloButton?.setOnClickListener {
            setOperator(Operator.MODULO)
        }

        val changeSignButton = findViewById<Button>(R.id.buttonChangeSign)
        changeSignButton?.setOnClickListener {
            changeSign()
        }

        val logarithmButton = findViewById<Button>(R.id.buttonLogarithm)
        logarithmButton?.setOnClickListener {
            logarithm()
        }

        val powerButton = findViewById<Button>(R.id.buttonPower)
        powerButton?.setOnClickListener {
            power()
        }

        val calculateButton = findViewById<Button>(R.id.buttonCalculate)
        calculateButton?.setOnClickListener {
            calculate()
        }

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton?.setOnClickListener {
            clear()
        }

        val backspaceButton = findViewById<Button>(R.id.buttonBackspace)
        backspaceButton?.setOnClickListener {
            backspace()
        }
    }
}