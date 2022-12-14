package com.fakhrirasyids.calmatrix.ui.calculate.fragment

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.fakhrirasyids.calmatrix.databinding.FragmentMatrixABinding
import com.fakhrirasyids.calmatrix.ui.adapter.GridViewAdapter

class MatrixAFragment : Fragment() {
    private var _binding: FragmentMatrixABinding? = null
    private lateinit var adapter: GridViewAdapter
    private val matrixViewModel by viewModels<MatrixViewModel>()

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatrixABinding.inflate(inflater, container, false)

        gridViewMatrixA = binding.gridViewMatrixA

        val spinnerSize = arrayOf(2, 3, 4, 5)
        binding.spinnerRows.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, spinnerSize)
        binding.spinnerColumns.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, spinnerSize)

        matrixViewModel.matrixA.observe(viewLifecycleOwner) {
            matrix = it
        }

        adapter = GridViewAdapter(
            requireContext(),
            matrix
        )
        matrixViewModel.matrixA.value = matrix
        setMatrixGrid()

        binding.spinnerRows.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, position: Long) {
                setMatrixGrid()
                clearMatrix(matrix)
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        binding.spinnerColumns.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    id: Int,
                    position: Long
                ) {
                    setMatrixGrid()
                    clearMatrix(matrix)
                    adapter.notifyDataSetChanged()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }


        binding.btnClear.setOnClickListener {
            clearMatrix(matrix)
            adapter.notifyDataSetChanged()
            setMatrixGrid()
        }

        return binding.root
    }

    private fun setMatrixGrid() {
        val rows = binding.spinnerRows.selectedItem.toString().toInt()
        val columns = binding.spinnerColumns.selectedItem.toString().toInt()
        matrix =
            Array(rows) { Array(columns) { 0f } }
        adapter = GridViewAdapter(requireContext(), matrix)
        setupMatrixEntry(columns, adapter)
        matrixViewModel.matrixA.value =
            Array(rows) { Array(columns) { 0f } }
    }

    private fun setupMatrixEntry(columns: Int, adapter: GridViewAdapter) {
        gridViewMatrixA.numColumns = columns
        gridViewMatrixA.adapter = adapter
    }

    private fun clearMatrix(matrix: Array<Array<Float>>) {
        for (i in matrix.indices)
            for (j in matrix[i].indices)
                matrix[i][j] = 0f
    }

    companion object {
        var matrix: Array<Array<Float>> = Array(2) {Array(2) {0f} }
        lateinit var gridViewMatrixA: GridView
    }
}