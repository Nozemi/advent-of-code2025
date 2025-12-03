package io.nozemi.aoc.library.types.matrix.interfaces

interface IFixedSizeMatrix<T> {
    val rows: Int
    val cols: Int

    fun inverse(): IFixedSizeMatrix<T>
    fun multiply(other: IFixedSizeMatrix<T>): IFixedSizeMatrix<T>
    val determinant: T

    operator fun get(row: Int, column: Int): T
    operator fun set(row: Int, column: Int, value: T)

    // TODO: Would this make sense?
    //   operator fun times(other: IFixedSizeMatrix<T>): IFixedSizeMatrix<T> = multiply(other)
}