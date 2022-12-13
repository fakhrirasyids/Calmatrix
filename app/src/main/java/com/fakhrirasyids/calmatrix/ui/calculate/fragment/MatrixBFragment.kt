package com.fakhrirasyids.calmatrix.ui.calculate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.fragment.app.viewModels
import com.fakhrirasyids.calmatrix.databinding.FragmentMatrixABinding
import com.fakhrirasyids.calmatrix.databinding.FragmentMatrixBBinding
import com.fakhrirasyids.calmatrix.ui.adapter.GridViewAdapter

class MatrixBFragment : Fragment() {
    private var _binding: FragmentMatrixBBinding? = null
    private lateinit var adapter: GridViewAdapter
    private val matrixViewModel by viewModels<MatrixViewModel>()

    private val binding get() = _binding!!
    private var matrix: Array<Array<Float>> = Array(2) { Array(2) { 0f } }
    private var rows: Int = 2
    private var columns: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatrixBBinding.inflate(inflater, container, false)

        gridViewMatrixB = binding.gridViewMatrixB

        val spinnerSize = arrayOf(2, 3, 4, 5)
        binding.spinnerRows.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerSize
            )
        binding.spinnerColumns.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerSize
            )

        matrixViewModel.matrixB.observe(viewLifecycleOwner) {
            matrix = it
        }

        adapter = GridViewAdapter(
            requireContext(),
            matrix
        )
        matrixViewModel.matrixB.value = matrix
        setMatrixGrid()

        binding.spinnerRows.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, id: Int, position: Long) {
                setMatrixGrid()
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
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }


        binding.btnClear.setOnClickListener {
            clearMatrix()
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    private fun setMatrixGrid() {
        rows = binding.spinnerRows.selectedItem.toString().toInt()
        columns = binding.spinnerColumns.selectedItem.toString().toInt()
        matrix =
            Array(rows) { Array(columns) { 0f } }
        adapter = GridViewAdapter(requireContext(), matrix)
        setupMatrixEntry(columns, adapter)
        matrixViewModel.matrixA.value =
            Array(rows) { Array(columns) { 0f } }
    }

    private fun setupMatrixEntry(columns: Int, adapter: GridViewAdapter) {
        gridViewMatrixB.numColumns = columns
        gridViewMatrixB.adapter = adapter
    }

    private fun clearMatrix() {
        setMatrixGrid()

        matrixViewModel.matrixB.value =
            Array(rows) { Array(columns) { 0f } }
    }

    companion object {
        lateinit var gridViewMatrixB: GridView
    }
}