package com.fakhrirasyids.calmatrix.utils

class GaussElimination {

    private var totalCoefficient = 0f
    private var determinant = 1.0f

    fun determinantByGauss(matrix: Array<Array<Float>>): Float {
        val aux = matrix.clone()
        for (i in aux.indices) {
            moveRows(aux, i)
            makeZeroBelow(aux, i)
        }
        return calculateDeterminant(aux)
    }

    private fun moveRows(matrix: Array<Array<Float>>, j: Int) {
        if (matrix[j][j] == 0f)
            for (i in j + 1 until matrix.size)
                if (matrix[i][j] != 0f) {
                    move(i, j, matrix)
                    determinant *= -1
                }
    }

    private fun move(row1: Int, row2: Int, matrix: Array<Array<Float>>) {
        val aux = matrix[row1]
        matrix[row1] = matrix[row2]
        matrix[row2] = aux
    }

    private fun makeZeroBelow(matrix: Array<Array<Float>>, j: Int) {
        for (i in j + 1 until matrix.size) {
            val coefficient = matrix[i][j] / matrix[j][j]
            totalCoefficient += coefficient
            for (k in j until matrix[i].size)
                matrix[i][k] = matrix[i][k] - coefficient * matrix[j][k]
        }
    }

    private fun calculateDeterminant(matrix: Array<Array<Float>>): Float {
        for (i in matrix.indices)
            determinant *= matrix[i][i]
        return determinant
    }

}