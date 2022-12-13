package com.fakhrirasyids.calmatrix.ui.calculate.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.fakhrirasyids.calmatrix.R
import com.fakhrirasyids.calmatrix.databinding.FragmentMatrixABinding
import com.fakhrirasyids.calmatrix.databinding.FragmentScalarKBinding


class ScalarKFragment : Fragment() {
    private var _binding: FragmentScalarKBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScalarKBinding.inflate(inflater, container, false)

        constraintLayout = binding.constraintLayout
        editText = binding.etScalar

        binding.btnClear.setOnClickListener {
            scalar = 0F
            binding.etScalar.setText("")
        }

        return binding.root
    }

    companion object {
        var scalar: Float = 0F
        lateinit var constraintLayout: ConstraintLayout
        lateinit var editText: EditText
    }
}