package io.nozemi.aoc.library.types.vector

import io.nozemi.aoc.library.types.Direction3D

interface IVector3<T> {
    val x: T
    val y: T
    val z: T

    operator fun plus(other: IVector3<T>): IVector3<T>
    operator fun plus(direction: Direction3D): IVector3<T>

    operator fun minus(other: IVector3<T>): IVector3<T>
    operator fun minus(direction: Direction3D): IVector3<T>

    operator fun times(other: IVector3<T>): IVector3<T>
    operator fun times(value: T): IVector3<T>

    operator fun div(other: IVector3<T>): IVector3<T>
    operator fun div(value: T): IVector3<T>

    fun distanceFrom(other: IVector3<T>): IVector3<T>
}