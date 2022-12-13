package com.fakhrirasyids.calmatrix.ui.calculate.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MatrixViewModel : ViewModel() {

    val matrixA = MutableLiveData<Array<Array<Float>>>(Array(2) { Array(2) { 0f } })

    val matrixB = MutableLiveData<Array<Array<Float>>>(Array(2) { Array(2) { 0f } })

    val resultAdd = MutableLiveData<Array<Array<Float>>>(Array(2) { Array(2) { 0f } })
    val resultAddLiveData: LiveData<Array<Array<Float>>> = resultAdd
}