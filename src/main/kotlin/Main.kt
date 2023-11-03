package org.example

fun main() {
    println("Hello World!")
}

object MyContext

class MyClass {

    context(MyContext)
    fun myMethod(): String {
        return "base"
    }

    context(MyContext)
    fun mySuspendedMethod(): String {
        return "base"
    }
}