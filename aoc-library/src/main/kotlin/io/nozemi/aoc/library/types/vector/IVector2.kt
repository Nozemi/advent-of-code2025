package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction2D

interface IVector2<T> {
    val x: T
    val y: T

    operator fun plus(other: IVector2<T>): IVector2<T>
    operator fun plus(direction: Direction2D): IVector2<T>

    operator fun minus(other: IVector2<T>): IVector2<T>
    operator fun minus(direction: Direction2D): IVector2<T>

    operator fun times(other: IVector2<T>): IVector2<T>
    operator fun times(value: T): IVector2<T>

    operator fun div(other: IVector2<T>): IVector2<T>
    operator fun div(value: T): IVector2<T>

    fun distanceFrom(other: IVector2<T>): IVector2<T>
}