package com.fakhrirasyids.calmatrix.utils

object MatrixOperations {

    fun add(
        matrix1: Array<Array<Float>>,
        matrix2: Array<Array<Float>>,
        matrixResult: Array<Array<Float>>
    ) {
        for (i: Int in matrix1.indices)
            for (j: Int in matrix1[0].indices)
                matrixResult[i][j] = matrix1[i][j] + matrix2[i][j]
    }

    fun subtract(
        matrix1: Array<Array<Float>>,
        matrix2: Array<Array<Float>>,
        matrixResult: Array<Array<Float>>
    ) {
        for (i: Int in matrix1.indices)
            for (j: Int in matrix1[0].indices)
                matrixResult[i][j] = matrix1[i][j] - matrix2[i][j]

    }

    fun multiply(
        matrix1: Array<Array<Float>>,
        matrix2: Array<Array<Float>>,
        matrixResult: Array<Array<Float>>
    ) {
        var result: Float
        for (i: Int in matrix2[0].indices) {
            for (j: Int in matrix1.indices) {
                result = 0f
                for (k: Int in matrix1[0].indices)
                    result += matrix1[j][k] * matrix2[k][i]
                matrixResult[j][i] = result
            }
        }
    }

    fun determinant(matrix1: Array<Array<Float>>): Float {
        val gaussElimination = GaussElimination()
        return gaussElimination.determinantByGauss(matrix1)
    }

    fun transpose(
        matrix1: Array<Array<Float>>,
        matrixResult: Array<Array<Float>>
    ) {
        for (i in matrix1.indices)
            for (j in matrix1[0].indices)
                matrixResult[j][i] = matrix1[i][j]
    }

    fun trace(matrix1: Array<Array<Float>>): Float {
        var result = 0F
        for (i in matrix1.indices)
            for (j in matrix1[0].indices)
                if (i == j)
                    result += matrix1[i][i]
        return result
    }

    fun scalar(
        matrix1: Array<Array<Float>>,
        scalar1: Float,
        matrixResult: Array<Array<Float>>
    ) {
        for (i: Int in matrix1.indices)
            for (j: Int in matrix1[0].indices)
                matrixResult[i][j] = matrix1[i][j] * scalar1
    }
}