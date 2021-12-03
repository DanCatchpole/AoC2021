package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile

class Day3 : Day {

    private val input = readFile("resources/day3.txt")
        .map { it.toCharArray().map { c -> Character.getNumericValue(c) } }

    override fun partA() {
        println(gamma() * epsilon())
    }

    override fun partB() {
        println(rating(1) * rating(0))
    }

    private fun gamma() : Int {
        var str = ""
        for (col in 0 until input[0].size) {
            val column = input.map { it[col] }
            val most = column.groupingBy { it }.eachCount().maxByOrNull { it.value }
            str += most?.key
        }
        return Integer.parseInt(str, 2)
    }

    private fun epsilon() : Int {
        var str = ""
        for (col in 0 until input[0].size) {
            val column = input.map { it[col] }
            val min = column.groupingBy { it }.eachCount().minByOrNull { it.value }
            str += min?.key
        }
        return Integer.parseInt(str, 2)
    }

    // tieBreak = 1 -> compute the Oxygen Rating, tieBreak = 0 -> compute the CO2 Rating
    private fun rating(tieBreak: Int) : Int {
        var currentList = input.toList()
        for (col in 0 until input[0].size) {
            val column = currentList.map { it[col] }
            val counts = column.groupingBy { it }.eachCount()

            val filter = if (counts[0] == counts[1]) {
                tieBreak
            } else if (counts[0]!! > counts[1]!!) {
                // more 0's -> use 0 if oxygen rating, 1 if CO2
                if (tieBreak == 1) 0 else 1
            } else {
                // more 1's -> use 1 if oxygen rating, 0 if CO2
                if (tieBreak == 1) 1 else 0
            }

            currentList = currentList.filter { it[col] == filter }
            if (currentList.size == 1) {
                return Integer.parseInt(currentList[0].map { "" + it }.joinToString(""), 2)
            }
        }
        return -1
    }
}