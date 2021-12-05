package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.Spring.height
import kotlin.math.roundToInt


class Day5 : Day {

    private val input = readFile("resources/day5.txt")
    private var ventMap = mutableMapOf<Pair<Int, Int>, Int>()

    private var maxX = 0
    private var maxY = 0

    init {
        rebuildMap(false)
    }

    private fun rebuildMap(diagonals: Boolean) {
        ventMap = mutableMapOf() // reset map
        for (line in input) {
            val coords = line.split(" -> ").map { it.split(",").map { a -> a.toInt() } }
            plotLine(Pair(coords[0][0], coords[0][1]), Pair(coords[1][0], coords[1][1]), diagonals)
            if (coords[0][0] > maxX) maxX = coords[0][0]
            if (coords[1][0] > maxX) maxX = coords[1][0]
            if (coords[0][1] > maxY) maxY = coords[0][1]
            if (coords[1][1] > maxY) maxY = coords[1][1]
        }
    }

    // Override so we can update coordinates
    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return Pair(this.first + other.first, this.second + other.second)
    }

    private fun plotLine(p1: Pair<Int, Int>, p2: Pair<Int, Int>, diagonals: Boolean) {
        var inc = Pair(0, 0)
        if (p1.first == p2.first && p1.second < p2.second) inc = Pair(0, 1)
        if (p1.first == p2.first && p1.second > p2.second) inc = Pair(0, -1)
        if (p1.second == p2.second && p1.first < p2.first) inc = Pair(1, 0)
        if (p1.second == p2.second && p1.first > p2.first) inc = Pair(-1, 0)

        if (diagonals && p1.first < p2.first && p1.second < p2.second) inc = Pair(1, 1)
        if (diagonals && p1.first > p2.first && p1.second > p2.second) inc = Pair(-1, -1)
        if (diagonals && p1.first < p2.first && p1.second > p2.second) inc = Pair(1, -1)
        if (diagonals && p1.first > p2.first && p1.second < p2.second) inc = Pair(-1, 1)

        // When we have a diagonal but are in part A
        if (inc == Pair(0, 0)) return

        var current = p1
        while (current != p2) {
            ventMap[current] = ventMap.getOrDefault(current, 0) + 1
            current += inc
        }
        ventMap[current] = ventMap.getOrDefault(current, 0) + 1 // finally at p2
    }

    override fun partA() {
        drawMap("resources/partA.png")
        return println(ventMap.values.count { it >= 2 })
    }

    override fun partB() {
        rebuildMap(true)
        drawMap("resources/partB.png")
        return println(ventMap.values.count { it >= 2 })
    }

    private fun printMap() {
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                print(ventMap.getOrDefault(Pair(x, y), 0).toString().replace('0', '.'))
            }
            println()
        }
    }

    private fun drawMap(fileName: String) {
        val img = BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_RGB)
        val max = ventMap.values.maxOf { it }.toFloat()

        for (y in 0 until maxY) {
            for (x in 0 until maxX) {
                val value = ventMap.getOrDefault(Pair(x, y), 0)
                val r = 0
                val g = ((value / max) * 255).roundToInt()
                val b = 0

                // draw the pixel
                val p = (r shl 16) or (g shl 8) or b
                img.setRGB(x, y, p)
            }
        }

        try {
            val f = File(fileName)
            ImageIO.write(img, "png", f)
        } catch (e: IOException) {
            println("Error: $e")
        }
    }
}