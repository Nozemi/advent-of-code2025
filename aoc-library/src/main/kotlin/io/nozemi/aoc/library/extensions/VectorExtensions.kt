package io.nozemi.aoc.library.extensions

import io.nozemi.aoc.library.types.vector.IVector2
import io.nozemi.aoc.library.types.vector.IVector3
import io.nozemi.aoc.library.types.vector.Vector2Long
import io.nozemi.aoc.library.types.vector.Vector3Long

val IVector2<Int>.asLong get() = Vector2Long(x.toLong(), y.toLong())
val IVector3<Int>.asLong get() = Vector3Long(x.toLong(), y.toLong(), z.toLong())