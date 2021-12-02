package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile

data class Position(var horiz: Int, var depth: Int, var aim: Int)

class Day2 : Day {

    private val input = readFile("resources/day2.txt")

    override fun partA() {
        var location : Pair<Int, Int> = Pair(0, 0) // horiz, depth
        for (command in input) {
            location += commandToPair(command)
        }
        println(location.first * location.second)
    }

    override fun partB() {
        val location = Position(0, 0, 0)
        for (command in input) {
            runCommand(location, command)
        }
        println(location.depth * location.horiz)
    }

    private fun commandToPair(command: String): Pair<Int, Int> {
        val splits = command.split(" ")
        val direction = splits[0]
        val distance = splits[1].toInt()

        return when (direction) {
            "forward" -> Pair(distance, 0)
            "down" -> Pair(0, distance)
            "up" -> Pair(0, -distance)
            else -> throw IllegalArgumentException("Invalid direction: $direction")
        }
    }

    private fun runCommand(location: Position, command: String) {
        val splits = command.split(" ")
        val direction = splits[0]
        val value = splits[1].toInt()

        when (direction) {
            "down" -> { location.aim += value }
            "up" -> { location.aim -= value }
            "forward" -> {
                location.horiz += value
                location.depth += location.aim * value
            }
            else -> throw IllegalArgumentException("Invalid direction: $direction")
        }
    }

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }
}