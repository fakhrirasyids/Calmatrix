package com.fakhrirasyids.calmatrix.ui.calculate.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.fakhrirasyids.calmatrix.R
import com.fakhrirasyids.calmatrix.databinding.FragmentMatrixABinding
import com.fakhrirasyids.calmatrix.databinding.FragmentResultBinding
import com.fakhrirasyids.calmatrix.ui.adapter.GridViewAdapter
import com.fakhrirasyids.calmatrix.ui.adapter.ResultGridViewAdapter
import com.fakhrirasyids.calmatrix.ui.calculate.CalculateActivity
import com.fakhrirasyids.calmatrix.utils.MatrixOperations

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private lateinit var adapter: ResultGridViewAdapter
    private val matrixViewModel by viewModels<MatrixViewModel>()

    private val binding get() = _binding!!
    private var matrix: Array<Array<Float>> = Array(2) { Array(2) { 0f } }
    private var rows: Int = 2
    private var columns: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        setMatrixGrid()

        binding.btnCalculate.setOnClickListener {
            if ((activity as CalculateActivity).getMessageType() == "Add") {
                calculateAdd()
            }
        }

        return binding.root
    }

    private fun calculateAdd() {
        if (matrixViewModel.matrixA.value!!.size != matrixViewModel.matrixB.value!!.size && matrixViewModel.matrixA.value!![0].size != matrixViewModel.matrixB.value!![0].size) {
            showToast("Matrix A and Matrix B size must same!")
        } else {
            if (checkMatrix("A") && checkMatrix("B")) {
                readMatrix("A")
                readMatrix("B")

                matrixViewModel.resultAdd.value =
                    MatrixOperations.add(
                        matrixViewModel.matrixA.value!!,
                        matrixViewModel.matrixB.value!!,
                        matrixViewModel.matrixA.value!!.size,
                        matrixViewModel.matrixA.value!![0].size
                    )
                showToast(matrixViewModel.resultAdd.value!![1][1].toString());
                showToast("CLEAR")

                setMatrixGrid()
                showResult()
            } else {
                showToast("Matrix must not be null!")
            }
        }
    }

    private fun setMatrixGrid() {
        adapter = ResultGridViewAdapter(
            requireContext(),
            matrix
        )
        adapter = ResultGridViewAdapter(requireContext(), matrix)
        setupMatrixEntry(columns, adapter)
        matrixViewModel.resultAdd.value = matrix
    }

    private fun setupMatrixEntry(columns: Int, adapter: ResultGridViewAdapter) {
        binding.gridViewMatrixResult.numColumns = columns
        binding.gridViewMatrixResult.adapter = adapter
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun clearMatrix() {
        setMatrixGrid()

        matrixViewModel.resultAdd.value =
            Array(rows) { Array(columns) { 0f } }
    }

    private fun showResult() {
        when ((activity as CalculateActivity).getMessageType()) {
            else -> {
                var itemView: View
                var editText: EditText

                for (i in matrix.indices)
                    for (j in matrix[i].indices) {
                        itemView =
                            binding.gridViewMatrixResult.getChildAt(i * matrixViewModel.resultAdd.value!![i].size + j)
                        editText = itemView.findViewById(R.id.et_item)
                        editText.setText(matrixViewModel.resultAdd.value!![i][j].toString())
                    }
            }
        }
    }

    private fun checkMatrix(type: String): Boolean {
        val gridViewMatrix =
            if (type == "A") MatrixAFragment.gridViewMatrixA else MatrixBFragment.gridViewMatrixB

        var itemView: View
        var editText: EditText

        for (i in matrixViewModel.matrixA.value!!.indices)
            for (j in matrixViewModel.matrixA.value!![i].indices) {
                itemView = gridViewMatrix.getChildAt(i * matrix[i].size + j)
                editText = itemView.findViewById(R.id.et_item)
                if (editText.text.toString().isEmpty()) return false
            }
        return true
    }

    private fun readMatrix(type: String) {
        val gridViewMatrix =
            if (type == "A") MatrixAFragment.gridViewMatrixA else MatrixBFragment.gridViewMatrixB

        var itemView: View
        var editText: EditText

        for (i in matrixViewModel.matrixA.value!!.indices)
            for (j in matrixViewModel.matrixA.value!![i].indices) {
                itemView = gridViewMatrix.getChildAt(i * matrix[i].size + j)
                editText = itemView.findViewById(R.id.et_item)
                matrix[i][j] = editText.text.toString().toFloat()
            }

        if (type == "A") matrixViewModel.matrixA.value =
            matrix.clone() else matrixViewModel.matrixB.value =
            matrix.clone()
    }
}