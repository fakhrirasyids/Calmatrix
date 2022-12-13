package com.fakhrirasyids.calmatrix.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fakhrirasyids.calmatrix.databinding.ActivityMainBinding
import com.fakhrirasyids.calmatrix.ui.calculate.CalculateActivity
import com.fakhrirasyids.calmatrix.utils.Constants.Companion.KEY_CALCULATE

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            btnAddMatrix.setOnClickListener {
                moveToCalculateActivity("Add")
            }
            btnSubtractMatrix.setOnClickListener {
                moveToCalculateActivity("Subtract")
            }
            btnMultiplyMatrix.setOnClickListener {
                moveToCalculateActivity("Multiply")
            }
            btnScalarMultiplyMatrix.setOnClickListener {
                moveToCalculateActivity("Scalar Multiply")
            }
            btnTransposeMatrix.setOnClickListener {
                moveToCalculateActivity("Transpose")
            }
            btnInverseMatrix.setOnClickListener {
                moveToCalculateActivity("Inverse")
            }
        }
    }

    private fun moveToCalculateActivity(message: String) {
        val iCalculate = Intent(this, CalculateActivity::class.java)
        iCalculate.putExtra(KEY_CALCULATE, message)
        startActivity(iCalculate)
    }
}