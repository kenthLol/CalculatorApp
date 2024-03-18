package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity()
{
    private var operationList = mutableListOf<String>()

    private var pn: Double = 0.0
    private var operation: String = ""

    private lateinit var btnNumberOne: Button
    private lateinit var btnNumberTwo: Button
    private lateinit var btnNumberThree: Button
    private lateinit var btnNumberFour: Button
    private lateinit var btnNumberFive: Button
    private lateinit var btnNumberSix: Button
    private lateinit var btnNumberSeven: Button
    private lateinit var btnNumberEight: Button
    private lateinit var btnNumberNine: Button
    private lateinit var btnNumberZero: Button

    private lateinit var btnErase: Button
    private lateinit var btnEraseOp: Button

    private lateinit var btnSwitchSign: Button
    private lateinit var btnDivision: Button
    private lateinit var btnMultiplication: Button
    private lateinit var btnSubtraction: Button
    private lateinit var btnAddition: Button

    private lateinit var btnEquals: Button
    private lateinit var btnDot: Button
    private lateinit var tvFormula: TextView
    private lateinit var tvResult: TextView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        InitComponents()
        InitListeners()
        InitUI()

    }

    private fun InitUI()
    {
        tvResult.visibility = View.VISIBLE
        tvResult.text = "0"
        tvFormula.visibility = View.VISIBLE
        tvFormula.text = "0"

        val numbers = arrayListOf(btnNumberOne, btnNumberTwo, btnNumberThree, btnNumberFour, btnNumberFive, btnNumberSix, btnNumberSeven, btnNumberEight, btnNumberNine, btnNumberZero)
        val operations = arrayListOf(btnDivision, btnMultiplication, btnSubtraction, btnAddition)

        for (b in numbers) {
            b.setOnClickListener {
                if (tvFormula.text.toString() != "0") {
                    tvFormula.text = tvFormula.text.toString() + b.text.toString()
                } else {
                    tvFormula.text = b.text.toString()
                }
            }
        }

        for (b in operations) {
            b.setOnClickListener {
                if (tvFormula.text.toString() != "0") {
                    operationList.add(tvFormula.text.toString())
                    operationList.add(b.text.toString())
                    tvFormula.text = ""
                }
            }
        }
    }

    private fun InitComponents()
    {
        btnNumberOne = findViewById(R.id.one)
        btnNumberTwo = findViewById(R.id.two)
        btnNumberThree = findViewById(R.id.three)
        btnNumberFour = findViewById(R.id.four)
        btnNumberFive = findViewById(R.id.five)
        btnNumberSix = findViewById(R.id.six)
        btnNumberSeven = findViewById(R.id.seven)
        btnNumberEight = findViewById(R.id.eight)
        btnNumberNine = findViewById(R.id.nine)
        btnNumberZero = findViewById(R.id.zero)

        btnErase = findViewById(R.id.erase)
        btnEraseOp = findViewById(R.id.allClearText)

        btnSwitchSign = findViewById(R.id.switchSign)
        btnDivision = findViewById(R.id.divide)
        btnMultiplication = findViewById(R.id.multiply)
        btnSubtraction = findViewById(R.id.subtraction)
        btnAddition = findViewById(R.id.addition)

        btnEquals = findViewById(R.id.equals)
        btnDot = findViewById(R.id.decimal)
        tvFormula = findViewById(R.id.tvFormula)
        tvResult = findViewById(R.id.tvResult)
    }

    private fun InitListeners()
    {
        btnEraseOp.setOnClickListener {
            pn = 0.0
            tvFormula.text = "0"
            tvResult.text = "0"
        }

        btnErase.setOnClickListener {
            val formula = tvFormula.text.split(" ")
            if (formula.size == 3) {
                tvFormula.text = "${formula[0]} ${formula[1]} "
            } else if (formula.size == 2) {
                tvFormula.text = ""
                pn = 0.0
            } else {
                val number = formula[0]
                if (number.length > 1) {
                    tvFormula.text = number.substring(0, number.length - 1)
                } else if (number.length == 1 && number != "0") {
                    tvFormula.text = "0"
                }
            }
        }

        btnEquals.setOnClickListener {
            if (tvFormula.text.toString() != "0") {
                operationList.add(tvFormula.text.toString())
                val result = evaluateOperationList()
                tvResult.text = result.toString()
                tvFormula.text = result.toString()
                operationList.clear()
            }
        }

        btnSwitchSign.setOnClickListener {
            val result = tvFormula.text.toString()
            try {
                val number = result.toDouble()
                val numberSignChanged = -number
                tvFormula.text = numberSignChanged.toString()
            } catch (e: NumberFormatException) {
                Toast.makeText(applicationContext, "Ingrese un número válido", Toast.LENGTH_SHORT).show()
            }
        }

        btnDot.setOnClickListener {
            if (!tvFormula.text.toString().contains(".")) {
                tvFormula.text = tvFormula.text.toString() + "."
            }
        }
    }
    private fun evaluateOperationList(): Double {
        var result = operationList[0].toDouble()
        for (i in 1 until operationList.size step 2) {
            val operator = operationList[i]
            val number = operationList[i + 1].toDouble()
            result = when (operator) {
                "/" -> result / number
                "x" -> result * number
                "+" -> result + number
                "-" -> result - number
                else -> result
            }
        }
        return result
    }
}