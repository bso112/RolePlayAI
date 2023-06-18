package com.bso112.roleplayai.android.util


val String.Companion.Empty inline get() = ""
fun <T> List<T>.addFirst(item: T): List<T> {
    return listOf(item) + this
}