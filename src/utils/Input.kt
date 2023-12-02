package utils

import kotlin.reflect.KClass

inline fun <reified T : Any> KClass<T>.readInput(day: Int) = T::class.java.getResourceAsStream("Input$day.txt").bufferedReader().readLines()
