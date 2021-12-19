package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile

data class Entry(val patterns: List<String>, val digits: List<String>)

class Day8 : Day {

    private val input = readFile("resources/day8.txt")
        .map {
            val split = it.split(" | ")
            Entry(split[0].split(" "), split[1].split(" "))
        }

    override fun partA() {
        println(input.sumOf { it.digits.count { it.length in listOf(2, 4, 3, 7) } })
    }

    override fun partB() {
        TODO("Not yet implemented")
    }

}
