package com.example.cuiduo.kotlin

import java.io.File

/**
 * Created by cuiduo on 2017/6/1.
 */
val a: Int = 0

var x: String = ""

fun sum(a: Int, b: Int) = a + b
fun main(args: Array<String>) {
    val a: Int = 1000
    var chuh : String
    var bcj: Long = 2483874
    val d = 2
    val c: Int
    c = 3
    for (x in 1..10 step 2) {
        print(x)
    }
    fun theAnswer(): Int = 42
    for (x in 9 downTo 0 step 3) {
        print(x)
    }
    val x = 10
    val y = 9

    if (x in 1..y + 1) {
        println("fits in range")
    }
    val list = listOf("a", "b", "c")

    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range too")
    }
    if (c > 6) {
        var sioioi = 1_000_000
        val boxedA: Int = a
        val anotherBoxedA: Int? = a
        print(boxedA === anotherBoxedA)
    }
    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    val items = listOf("apple", "banana", "kiwi")
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }
    fun describe(obj: Any): String =
            when (obj) {
                1 -> "One"
                "Hello" -> "Greeting"
                is Long -> "Long"
                !is String -> "Not a string"
                else -> "Unknown"
            }

    val files = File("test").listFiles()
    println(files?.size ?: "empty")
}
data class Customer(val name: String, val email: String)

