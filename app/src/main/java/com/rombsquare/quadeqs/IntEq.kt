import kotlin.random.Random

class IntEq {
    var diff = "medium"
    var text = ""
    var solved = -1
    var roots = listOf<Int>()
    var eq = listOf<Int>()

    var minX: Int = 0
    var maxX: Int = 0
    var minA: Int = 0
    var maxA: Int = 0


    constructor(diff: String, minX: Int = 0, maxX: Int = 0, minA: Int = 0, maxA: Int = 0) {
        this.diff = diff

        this.minX = minX
        this.maxX = maxX
        this.minA = minA
        this.maxA = maxA

        next()
    }

    companion object {
        fun iqeqByRoots(x1: Int, x2: Int, a: Int = 1): List<List<Int>> {
            val b = (x1 + x2) * -a
            val c = x1 * x2 * a

            return listOf(listOf(a, b, c), listOf(x1, x2))
        }

        fun randomIqeq(minRoot: Int, maxRoot: Int, minA: Int = 1, maxA: Int = 1): List<List<Int>> {
            var x1 = 0
            var x2 = 0
            var a = 0

            if (minRoot == maxRoot) {
                x1 = minRoot
                x2 = minRoot
            } else {
                x1 = Random.nextInt(minRoot, maxRoot)
                x2 = Random.nextInt(minRoot, maxRoot)
            }

            if (minA == maxA) {
                a = minA
            } else {
                a = Random.nextInt(minA, maxA)
            }

            if (a == 0) {
                a = 1
            }

            return iqeqByRoots(x1, x2, a)
        }

        fun genIqeqByDiff(diff: String): List<List<Int>> {
            var minRoot = 0
            var maxRoot = 0
            var minA = 1
            var maxA = 1

            when (diff) {
                "simple" -> {
                    minRoot = 1
                    maxRoot = 10
                }

                "classic" -> {
                    minRoot = -10
                    maxRoot = 10
                }

                "advanced" -> {
                    minRoot = -10
                    maxRoot = 10
                    minA = -3
                    maxA = 3
                }

                "pro" -> {
                    minRoot = -20
                    maxRoot = 20
                    minA = -5
                    maxA = 5
                }

                "reduced_master_1" -> {
                    minRoot = 1
                    maxRoot = 30
                }

                "reduced_master_2" -> {
                    minRoot = -30
                    maxRoot = 30
                }

                "senior_1" -> {
                    minRoot = 1
                    maxRoot = 10
                    minA = -10
                    maxA = 10
                }

                "senior_2" -> {
                    minRoot = -10
                    maxRoot = 10
                    minA = -10
                    maxA = 10
                }
            }

            return randomIqeq(minRoot, maxRoot, minA, maxA)
        }

        fun genIqeqGrowth(solved: Int): List<List<Int>> {
            var minX = -2 - solved/2
            var maxX = 2 + solved/2

            var minA = 1
            var maxA = 1

            if (solved >= 10) {
                minA = -1 - (solved-10)/20
                maxA = 1 + (solved-10)/20
            }

            return randomIqeq(minX, maxX, minA, maxA)

        }

        fun format(eq: List<Int>): String {
            var stringA: String = java.lang.String.valueOf(eq.get(0))
            var stringB: String = java.lang.String.valueOf(eq.get(1))
            val stringC: String = java.lang.String.valueOf(eq.get(2))

            if (eq.get(0) === 1) {
                stringA = ""
            } else if (eq.get(0) === -1) {
                stringA = "-"
            }

            if (eq.get(1) === 1) {
                stringB = ""
            } else if (eq.get(1) === -1) {
                stringB = "-"
            }

            var formatted = ""
            if (eq.get(1) === 0 && eq.get(2) === 0) {
                formatted = String.format("%sx² = 0", stringA)
            } else if (eq.get(1) === 0) {
                formatted = String.format("%sx² + %s = 0", stringA, stringC)
            } else if (eq.get(2) === 0) {
                formatted = String.format("%sx² + %sx = 0", stringA, stringB)
            } else {
                formatted = String.format("%sx² + %sx + %s = 0", stringA, stringB, stringC)
            }

            return formatted.replace("+ -", "- ")

        }

    }

    fun genCustom(): List<List<Int>> {
        return randomIqeq(minX, maxX, minA, maxA)
    }

    fun next(userRoots: List<Int> = emptyList()): Int {
        if (solved != -1 && userRoots != roots.sorted() && userRoots != emptyList<Int>()) {
            return -1
        }

        var eqroots: List<List<Int>> = emptyList()

        if (diff == "growth") {
            eqroots = genIqeqGrowth(solved)
        } else if (diff == "custom") {
            eqroots = genCustom()
        } else {
            eqroots = genIqeqByDiff(diff)
        }

        eq = eqroots[0]
        roots = eqroots[1]
        text = format(eq)

        solved++

        return 0
    }

    fun nextWithoutRoots(): Int {
        return next(roots)
    }
}

fun main() {
    val eq = IntEq("hard")

    println(eq.eq)

    eq.next(eq.roots)

    println(eq.eq)
}