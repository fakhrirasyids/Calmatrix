package com.fakhrirasyids.calmatrix.utils

object MatrixOperations {
    fun add(
        matrix1: Array<Array<Float>>,
        matrix2: Array<Array<Float>>,
        row: Int,
        col: Int
    ): Array<Array<Float>> {
        var matrixResult: Array<Array<Float>> = Array(row) { Array(col) { 0f } }
        for (i: Int in matrix1.indices)
            for (j: Int in matrix1[0].indices)
                matrixResult[i][j] = matrix1[i][j] + matrix2[i][j]

        return matrixResult
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

    fun transpose(
        matrix1: Array<Array<Float>>,
        matrixResult: Array<Array<Float>>
    ) {
        for (i in matrix1.indices)
            for (j in matrix1[0].indices)
                matrixResult[j][i] = matrix1[i][j]
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