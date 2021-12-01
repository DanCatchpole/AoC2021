package me.dcatcher.aoc.utils

import java.io.File

fun readFile(fileName: String): List<String> {
    return File(fileName).bufferedReader().readLines()
}