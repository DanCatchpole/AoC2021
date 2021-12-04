package me.dcatcher.aoc

import me.dcatcher.aoc.utils.readFile

class BingoBoard(height: Int, width: Int, vals: List<Int>) {

    private val matrix: Array<Array<Pair<Int, Boolean>>>
    private val height: Int
    private val width: Int

    init {
        this.matrix = Array(height) { Array(width) { Pair(0, false) } }
        this.height = height
        this.width = width

        // Fill the matrix with the values
        for (i in 0 until height) {
            for (j in 0 until width) {
                matrix[i][j] = Pair(vals[i * width + j], false)
            }
        }
    }

    private fun get(row: Int, col: Int): Pair<Int, Boolean> {
        return matrix[row][col]
    }

    fun markIfPresent(value: Int) {
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (get(row, col).first == value) {
                    mark(row, col)
                }
            }
        }
    }

    private fun mark(row: Int, col: Int) {
        matrix[row][col] = Pair(matrix[row][col].first, true)
    }

    private fun hasMarkedRow(): Boolean {
        for (row in 0 until height) {
            if (isRowMarked(row)) {
                return true
            }
        }
        return false
    }

    private fun hasMarkedColumn(): Boolean {
        for (col in 0 until width) {
            if (isColumnMarked(col)) {
                return true
            }
        }
        return false
    }

    private fun isRowMarked(row: Int): Boolean {
        for (col in 0 until width) {
            if (!matrix[row][col].second) {
                return false
            }
        }
        return true
    }

    private fun isColumnMarked(col: Int): Boolean {
        for (row in 0 until height) {
            if (!matrix[row][col].second) {
                return false
            }
        }
        return true
    }

    fun bingo(): Boolean {
        return hasMarkedRow() || hasMarkedColumn()
    }

    fun sumNotMarked(): Int {
        var sum = 0
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (!matrix[row][col].second) {
                    sum += matrix[row][col].first
                }
            }
        }
        return sum
    }
}

class Day4 : Day {

    private val drawList: List<Int>
    private val boards: MutableList<BingoBoard> = mutableListOf()

    init {
        val input = readFile("resources/day4.txt")
        this.drawList = input[0].split(",").map { it.toInt() }

        for (startLine in 2 until input.size step 6) {
            val vals = input.slice(startLine..startLine + 4)
                .joinToString(" ")
                .split(" ")
                .filter { it.isNotEmpty() } // Remove empty strings (due to "  ")
                .map { it.toInt() }
            this.boards.add(BingoBoard(5, 5, vals))
        }
    }

    override fun partA() {
        for (number in drawList) {
            for (board in boards) {
                board.markIfPresent(number)
                if (board.bingo()) {
                    return println(board.sumNotMarked() * number)
                }
            }
        }
    }

    override fun partB() {
        var currentScore = 0
        for (number in drawList) {
            val boardsToRemove: MutableList<BingoBoard> = mutableListOf()
            for (board in boards) {
                board.markIfPresent(number)
                if (board.bingo()) {
                    currentScore = board.sumNotMarked() * number
                    boardsToRemove.add(board)
                }
            }
            boards.removeAll(boardsToRemove)
        }
        return println(currentScore)
    }
}