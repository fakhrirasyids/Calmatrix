package com.fakhrirasyids.calmatrix.ui.calculate.fragment

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.fakhrirasyids.calmatrix.R
import com.fakhrirasyids.calmatrix.databinding.FragmentResultBinding
import com.fakhrirasyids.calmatrix.ui.adapter.GridViewAdapter
import com.fakhrirasyids.calmatrix.ui.adapter.ResultGridViewAdapter
import com.fakhrirasyids.calmatrix.ui.calculate.CalculateActivity
import com.fakhrirasyids.calmatrix.utils.InverseOperations
import com.fakhrirasyids.calmatrix.utils.MatrixOperations
import com.google.android.material.button.MaterialButton

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private lateinit var adapter: ResultGridViewAdapter
    private val matrixViewModel by viewModels<MatrixViewModel>()

    private val binding get() = _binding!!

    private var trace: Float = 0F
    private var determinant: Float = 0F
    private var scalar: Float = 0F
    private var matrix: Array<Array<Float>> = Array(2) { Array(2) { 0f } }

    private lateinit var matrixA: Array<Array<Float>>
    private lateinit var matrixB: Array<Array<Float>>
    private lateinit var gridViewMatrixResult: GridView
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        type = (activity as CalculateActivity).getMessageType()
        binding.btnCalculate.text = type
        btnResult = binding.btnResult
        btnResult.isEnabled = false
        gridViewMatrixResult = binding.gridViewMatrixResult

        if (type == "Determinant" || type == "Trace") {
            binding.etResult.visibility = View.VISIBLE
            binding.tvResult.visibility = View.VISIBLE
            binding.tvResult.text = if (type == "Determinant") "det(A) = " else "tr(A) = "
        } else {
            setupMatrixEntry(binding.root)
        }

        val message1 = if (type == "Add" || type == "Subtract" || type == "Multiply") {
            "Matrix A and Matrix B must be complete"
        } else if (type == "Determinant" || type == "Trace" || type == "Inverse" || type == "Transpose") {
            "Matrix A must be complete"
        } else {
            "Matrix A and Scalar K must be complete"
        }

        val message2 = if (type == "Add" || type == "Subtract") {
            "The size of both matrix must be same"
        } else if (type == "Multiply") {
            "Size of column Matrix A and row Matrix B must be same"
        } else if (type == "Determinant" || type == "Trace" || type == "Inverse") {
            "Matrix A must be square"
        } else {
            "" // for transpose and scalar multipy
        }

        binding.btnClear.setOnClickListener {
            clearMatrix(matrix)
            adapter.notifyDataSetChanged()
            setupMatrixEntry(binding.root)
            btnResult.isEnabled = false
//            btn_calculate.setBackgroundResource(R.drawable.bg_btn)
//            btn_calculate.setTextColor(Color.parseColor("#3C4473"))
        }

        binding.btnCalculate.setOnClickListener {
            matrixA = MatrixAFragment.matrix
            matrixB =
                if (type == "Add" || type == "Subtract" || type == "Multiply") MatrixBFragment.matrix else Array(
                    2
                ) { Array(2) { 0f } }
            val isMatrixAEmpty = checkMatrix("A")
            val isMatrixBEmpty =
                if (type == "Add" || type == "Subtract" || type == "Multiply") checkMatrix("B") else false
            val isScalarKEmpty = if (type == "Scalar Multiply") checkScalar() else false

            if (!isMatrixAEmpty) {
                readMatrix("A")
                matrixA = MatrixAFragment.matrix
            }

            if (type == "Add" || type == "Subtract" || type == "Multiply") {
                if (!isMatrixBEmpty) {
                    readMatrix("B")
                    matrixB = MatrixBFragment.matrix
                }
            }
//
            if (type == "Scalar Multiply") {
                if (!isScalarKEmpty) {
                    readScalar()
                }
            }

            val condition1 = if (type == "Add" || type == "Subtract" || type == "Multiply") {
                (!isMatrixAEmpty && !isMatrixBEmpty)
            }
//            else if (type == "Determinant" || type == "Trace" || type == "Inverse" || type == "Transpose") {
//                (!isMatrixAEmpty)
//            }
            else {
                (!isMatrixAEmpty)
            }

            val condition2 = when (type) {
                "Add", "Subtract" -> {
                    (matrixA.size == matrixB.size && matrixA[0].size == matrixB[0].size)
                }
                "Multiply" -> {
                    (matrixA[0].size == matrixB.size)
                }
                "Determinant", "Trace", "Inverse" -> {
                    (matrixA.size == matrixA[0].size)
                }
                else -> {
                    false // for transpose and scalar multipy
                }
            }

            when (type) {
                "Transpose", "Scalar Multiply" -> {
                    if (condition1) {
                        setupMatrixEntry(binding.root)
                        btnResult.isEnabled = true
                    } else {
                        setToast(binding.root, message1)
                    }
                }
                "Determinant", "Trace" -> {
                    btnResult.isEnabled = true
                }
                else -> {
                    if (condition1) {
                        if (condition2) {
                            setupMatrixEntry(binding.root)
                            btnResult.isEnabled = true
                        } else {
                            setToast(binding.root, message2)
                        }
                    } else {
                        setToast(binding.root, message1)
                    }
                }
            }

//            if (binding.btnResult.isEnabled) {
//                btn_calculate.setBackgroundResource(R.drawable.bg_btn_select)
//                btn_calculate.setTextColor(Color.parseColor("#FFFFFF"))
//            } else {
//                binding.btnCalculate.setBackgroundResource(R.drawable.bg_btn)
//            }
        }

        btnResult.setOnClickListener {
//            var matrixA = MatrixAFragment.matrix
//            var matrixB = if (type == "Add" || type == "Subtract" || type == "Multiply") MatrixBFragment.matrix else Array(2) {Array(2) {0f} }
            val isMatrixAEmpty = checkMatrix("A")
            val isMatrixBEmpty =
                if (type == "Add" || type == "Subtract" || type == "Multiply") checkMatrix("B") else false
            val isScalarKEmpty = if (type == "Scalar Multiply") checkScalar() else false

            if (!isMatrixAEmpty) {
                readMatrix("A")
            }

            if (type == "Add" || type == "Subtract" || type == "Multiply") {
                if (!isMatrixBEmpty) {
                    readMatrix("B")
                }
            }

            if (type == "Scalar Multiply") {
                if (!isScalarKEmpty) {
                    readScalar()
                }
            }

            when (type) {
                "Add" -> MatrixOperations.add(matrixA, matrixB, matrix)
                "Subtract" -> MatrixOperations.subtract(matrixA, matrixB, matrix)
                "Multiply" -> MatrixOperations.multiply(matrixA, matrixB, matrix)
                "Determinant" -> determinant = MatrixOperations.determinant(matrixA)
                "Trace" -> trace = MatrixOperations.trace(matrixA)
                "Inverse" -> InverseOperations.mainInverse(matrixA, matrix)
                "Transpose" -> MatrixOperations.transpose(matrixA, matrix)
                "Scalar Multiply" -> MatrixOperations.scalar(matrixA, scalar, matrix)
            }

            showResult()

            btnResult.isEnabled = false
        }

        return binding.root
    }

    private fun setToast(view: View, msg: String) {
        Toast.makeText(view.context, msg, Toast.LENGTH_LONG).show()
    }

    private fun setupMatrixEntry(view: View) {
        val rows =
            if (type == "Add" || type == "Subtract" || type == "Multiply" || type == "Inverse" || type == "Scalar Multiply") {
                MatrixAFragment.matrix.size
            } else {
                MatrixAFragment.matrix[0].size
            }

        val columns =
            if (type == "Add" || type == "Subtract" || type == "Inverse" || type == "Scalar Multiply") {
                MatrixAFragment.matrix[0].size
            } else if (type == "Multiply") {
                MatrixBFragment.matrix[0].size
            } else {
                MatrixAFragment.matrix.size
            }

        matrix = Array(rows) { Array(columns) { 0f } }
        adapter = ResultGridViewAdapter(
            view.context,
            matrix
        )
        gridViewMatrixResult.numColumns = columns
        gridViewMatrixResult.adapter = adapter
    }

    private fun clearMatrix(matrix: Array<Array<Float>>) {
        when (type) {
            "Determinant" -> {
                determinant = 0F
                binding.etResult.setText("")
            }
            "Trace" -> {
                trace = 0F
                binding.etResult.setText("")
            }
            else -> {
                for (i in matrix.indices)
                    for (j in matrix[i].indices)
                        matrix[i][j] = 0f
            }
        }
    }

    private fun checkMatrix(type: String): Boolean {
        val matrix = if (type == "A") MatrixAFragment.matrix else MatrixBFragment.matrix
        val gridViewMatrix =
            if (type == "A") MatrixAFragment.gridViewMatrixA else MatrixBFragment.gridViewMatrixB

        var itemView: View
        var editText: EditText

        for (i in matrix.indices)
            for (j in matrix[i].indices) {
                itemView = gridViewMatrix.getChildAt(i * matrix[i].size + j)
                editText = itemView.findViewById(R.id.et_item)
                if (editText.text.toString().isEmpty()) return true
            }
        return false
    }

    private fun readMatrix(type: String) {
        val matrix = if (type == "A") MatrixAFragment.matrix else MatrixBFragment.matrix
        val gridViewMatrix =
            if (type == "A") MatrixAFragment.gridViewMatrixA else MatrixBFragment.gridViewMatrixB

        var itemView: View
        var editText: EditText

        for (i in matrix.indices)
            for (j in matrix[i].indices) {
                itemView = gridViewMatrix.getChildAt(i * matrix[i].size + j)
                editText = itemView.findViewById(R.id.et_item)
                matrix[i][j] = editText.text.toString().toFloat()
            }

        if (type == "A") MatrixAFragment.matrix = matrix.clone() else MatrixBFragment.matrix =
            matrix.clone()
    }

    private fun checkScalar(): Boolean {
        val itemView =
            ScalarKFragment.constraintLayout
        val editText = itemView.findViewById<EditText>(R.id.et_scalar)

        if (editText.text.toString().isEmpty()) return true
        return false
    }

    private fun readScalar() {
        val itemView =
            ScalarKFragment.constraintLayout
        val editText = itemView.findViewById<EditText>(R.id.et_scalar)
        scalar = editText.text.toString().toFloat()
    }

    private fun showResult() {
        when (type) {
            "Determinant" -> binding.etResult.setText(getString(R.string.fromat).format(determinant))
            "Trace" -> binding.etResult.setText(trace.toString())
            else -> {
                var itemView: View
                var editText: EditText

                for (i in matrix.indices)
                    for (j in matrix[i].indices) {
                        itemView = gridViewMatrixResult.getChildAt(i * matrix[i].size + j)
                        editText = itemView.findViewById(R.id.et_item)
                        editText.setText(matrix[i][j].toString())
                    }
            }
        }
    }

    companion object {
        lateinit var btnResult: MaterialButton
    }
}