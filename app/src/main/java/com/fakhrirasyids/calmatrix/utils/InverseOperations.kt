package com.fakhrirasyids.calmatrix.utils

object InverseOperations {
    //    const val N = 3
    // Function to get cofactor of A[p][q] in temp[][]. n is current
    // dimension of A[][]
    private fun getCofactor(A: Array<Array<Float>>, temp: Array<Array<Float>>, p: Int, q: Int, n: Int) {
        var i = 0
        var j = 0
        // Looping for each element of the matrix
        for (row in 0 until n) {
            for (col in 0 until n) { // Copying into temporary matrix only those element
                // which are not in given row and column
                if (row != p && col != q) {
                    temp[i][j++] = A[row][col]
                    // Row is filled, so increase row index and
                    // reset col index
                    if (j == n - 1) {
                        j = 0
                        i++
                    }
                }
            }
        }
    }

    /* Recursive function for finding determinant of matrix.
    n is current dimension of A[][]. */
    private fun determinant(A: Array<Array<Float>>, n: Int): Float {

        val x = A.size

        var d = 0F // Initialize result
        // Base case : if matrix contains single element
        if (n == 1) return A[0][0]
        val temp: Array<Array<Float>>  // To store cofactors
        temp = Array(x) {Array(x) {0f} } //Array(N) { FloatArray(N) }
        var sign = 1 // To store sign multiplier
        // Iterate for each element of first row
        for (f in 0 until n) { // Getting Cofactor of A[0][f]
            getCofactor(
                A,
                temp,
                0,
                f,
                n
            )
            d += sign * A[0][f] * determinant(
                temp,
                n - 1
            )
            // terms are to be added with alternate sign
            sign = -sign
        }
        return d
    }

    // Function to get adjoint of A[N][N] in adj[N][N].
    private fun adjoint(A: Array<Array<Float>>, adj: Array<Array<Float>>) {

        val n = A.size

        if (n == 1) {
            adj[0][0] = 1F
            return
        }
        // temp is used to store cofactors of A[][]
        var sign: Int
        val temp = Array(n) {Array(n) {0f} }
        for (i in 0 until n) {
            for (j in 0 until n) { // Get cofactor of A[i][j]
                getCofactor(
                    A,
                    temp,
                    i,
                    j,
                    n
                )
                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = if ((i + j) % 2 == 0) 1 else -1
                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj[j][i] = sign * determinant(
                    temp,
                    n - 1
                )
            }
        }
    }

    // Function to calculate and store inverse, returns false if
    // matrix is singular
    private fun inverse(
        A: Array<Array<Float>>,
        inverse: Array<Array<Float>>
    ): Boolean { // Find determinant of A[][]

        val n = A.size

        val det =
            determinant(A, n)
        if (det == 0F) {
            print("Singular matrix, can't find its inverse")
            return false
        }
        // Find adjoint
        val adj = Array(n) {Array(n) {0f} }//Array(N) { IntArray(N) }
        adjoint(A, adj)
        // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
        for (i in 0 until n) for (j in 0 until n) inverse[i][j] =
            adj[i][j] / det
        return true
    }

    fun mainInverse(
        matrix1: Array<Array<Float>>,
        matrixResult: Array<Array<Float>>
    ) {
        val n = matrix1.size

        val adj =
            Array(n) {Array(n) {0f} } // To store adjoint of A[][]
        val inv =
            Array(n) {Array(n) {0f} } // To store inverse of A[][]

        adjoint(matrix1, adj)

        if (inverse(
                matrix1,
                inv
            )
        )
            for (i: Int in inv.indices)
                for (j: Int in inv[0].indices)
                    matrixResult[i][j] = inv[i][j]
    }
}