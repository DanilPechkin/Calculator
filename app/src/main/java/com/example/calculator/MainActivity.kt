package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var iszap: Int = 0
    var leftscobk: Int = 0
    var rightscobk: Int = 0

    val znaki: Array<Char> = arrayOf('+', '-', '×', '÷')
    val forznaki: Array<Char> = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ')')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_1.setOnClickListener { setSymbol("1") }
        btn_2.setOnClickListener { setSymbol("2") }
        btn_3.setOnClickListener { setSymbol("3") }
        btn_4.setOnClickListener { setSymbol("4") }
        btn_5.setOnClickListener { setSymbol("5") }
        btn_6.setOnClickListener { setSymbol("6") }
        btn_7.setOnClickListener { setSymbol("7") }
        btn_8.setOnClickListener { setSymbol("8") }
        btn_9.setOnClickListener { setSymbol("9") }
        btn_0.setOnClickListener { setSymbol("0") }
        btn_plus.setOnClickListener { setZnak("+") }
        btn_minus.setOnClickListener { setZnak("-") }
        btn_umnoz.setOnClickListener { setZnak("×") }
        btn_div.setOnClickListener { setZnak("÷") }

        btn_zap.setOnClickListener {
            if (mathOperation.text.isNotEmpty())
                if (mathOperation.text[mathOperation.text.length - 1].isDigit())
                    if (iszap == 0) {
                        setSymbol(",")
                        iszap = 1
                    }
        }
        btn_lefts.setOnClickListener {
            setSymbol("(")
            leftscobk += 1
        }
        btn_rights.setOnClickListener {
            if (leftscobk > rightscobk)
                if (mathOperation.text[mathOperation.text.length - 1] != '(') {
                    setSymbol(")")
                    rightscobk += 1
                }
        }
        btn_ac.setOnClickListener {
            mathOperation.setText("")
            resultText.setText("")
            iszap = 0
        }
        btn_back.setOnClickListener {
            if (mathOperation.text.isNotEmpty()){
                mathOperation.text = mathOperation.text.substring(0, mathOperation.text.length - 1)
            }
        }
        btn_equals.setOnClickListener {
            try {
                val text = mathOperation.text.toString().replace('÷', '/').replace('×', '*').replace(',', '.')
                val ex = ExpressionBuilder(text).build()
                val result = ex.evaluate()

                if (result.toLong().toDouble() == result)
                    resultText.text = result.toLong().toString()
                else
                    resultText.text = result.toString().replace('.', ',')
            }
            catch (e: Exception){
                resultText.text = "Ошибка"
            }
        }
    }

    fun setSymbol (str: String){
        if (resultText.text != "") {
            mathOperation.text = resultText.text
            resultText.text = ""
        }
        mathOperation.append(str)
    }

    fun setZnak (str: String){
        if (mathOperation.text.isNotEmpty())
            if (forznaki.contains(mathOperation.text[mathOperation.text.length - 1])) {
                setSymbol(str)
                iszap = 0
            }
            else if (znaki.contains(mathOperation.text[mathOperation.text.length - 1])) {
                mathOperation.text = mathOperation.text.substring(0, mathOperation.text.length - 1)
                setSymbol(str)
                iszap = 0
            }
    }
}