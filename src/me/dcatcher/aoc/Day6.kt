package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile

class Day6 : Day {

    private val fish = readFile("resources/day6.txt")[0]
        .split(',')
        .map { it.toInt() }
        .groupBy { it }

    private var fishMap = Array<Long>(9) { 0 }

    init {
        for (p in fish) {
            fishMap[p.key] = p.value.size.toLong()
        }
    }

    override fun partA() {
        for (i in 0 until 80) {
            doIteration()
        }
        println(fishMap.sum())
    }

    override fun partB() {
        // Start at 80 to keep state
        for (i in 80 until 256) {
            doIteration()
        }
        println(fishMap.sum())
    }

    private fun doIteration() {
        val newFish = Array<Long>(9) { 0 }
        for (i in 8 downTo 1) {
            newFish[i-1] = fishMap[i]
        }
        newFish[6] += fishMap[0]
        newFish[8] = fishMap[0]

        fishMap = newFish
    }
}