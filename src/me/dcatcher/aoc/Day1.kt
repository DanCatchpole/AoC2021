package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile

class Day1 : Day {

    private val input: List<Int> = readFile("resources/day1.txt").map { it.toInt() }

    override fun partA() {
        println((0 until input.size-1).map { Pair(input[it], input[it + 1]) }.count { it.second > it.first })
    }

    override fun partB() {
        val windowSums = (0 until input.size-2).map { input[it] + input[it + 1] + input[it + 2] }
        println((0 until windowSums.size-1).map { Pair(windowSums[it], windowSums[it + 1]) }
            .count { it.second > it.first })
    }
}
