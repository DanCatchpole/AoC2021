package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile
import kotlin.math.abs

class Day7 : Day {

    private var input = readFile("resources/day7.txt")[0].split(",").map { it.toInt() }

    override fun partA() {
        var bestCost = Int.MAX_VALUE
        var bestPos = -1
        for (i in input.minOf{it} .. input.maxOf {it}) {
            val cost = input.sumOf { abs(i - it) }
            if (cost < bestCost) {
                bestCost = cost
                bestPos = i
            }
        }
        println("Cost of $bestCost at pos $bestPos")
    }

    override fun partB() {
        var bestCost = Int.MAX_VALUE
        var bestPos = -1
        for (i in input.minOf{it} .. input.maxOf {it}) {
            val cost = input.map { abs(i - it) }.sumOf { it * (it + 1) / 2 }
            if (cost < bestCost) {
                bestCost = cost
                bestPos = i
            }
        }
        println("Cost of $bestCost at pos $bestPos")
    }

}
