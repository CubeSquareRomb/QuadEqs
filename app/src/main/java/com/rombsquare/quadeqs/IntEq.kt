import kotlin.random.Random

// This class generates quadratic equation with integer roots and coefficients & constant
class IntEq {
    // Initialize the data
    var diff = "medium" // Difficulty
    var text = "" // Used for formatted equation
    var solved = -1 // Total solved equations. When IntEq initalized, solved is 0
    var roots = listOf<Int>() // Roots after equation generation
    var eq = listOf<Int>() // Equation's coefficients and constant
    var simpleMode = false // Simple mode
    var minX: Int = 0 // Min root
    var maxX: Int = 0 // Max root
    var minA: Int = 0 // Min senior coeff
    var maxA: Int = 0 // Max senior coeff

    // IntEq settings
    constructor(diff: String, simpleMode: Boolean = false, minX: Int = 0, maxX: Int = 0, minA: Int = 0, maxA: Int = 0) {
        this.diff = diff
        this.simpleMode = simpleMode

        this.minX = minX
        this.maxX = maxX
        this.minA = minA
        this.maxA = maxA

        // Initialize equation
        next()
    }

    companion object {
        // iqeq = integer quadratic equation. Generate equation by roots
        fun iqeqByRoots(x1: Int, x2: Int, a: Int = 1): List<List<Int>> {
            val b = (x1 + x2) * -a
            val c = x1 * x2 * a

            return listOf(listOf(a, b, c), listOf(x1, x2))
        }

        // Generate random equation by root and senior coeff range
        fun randomIqeq(minRoot: Int, maxRoot: Int, minA: Int = 1, maxA: Int = 1): List<List<Int>> {
            // Initialize data
            var x1 = 0
            var x2 = 0
            var a = 0

            // If min root equals to the max root, both roots are min root.
            // Else: generate random roots
            if (minRoot == maxRoot) {
                x1 = minRoot
                x2 = minRoot
            } else {
                x1 = Random.nextInt(minRoot, maxRoot)
                x2 = Random.nextInt(minRoot, maxRoot)
            }

            // If min and max senior coeffs are the same, senior coeff is min senior coeff
            // Else: generate random
            if (minA == maxA) {
                a = minA
            } else {
                a = Random.nextInt(minA, maxA)
            }

            // If senior coeff is 0, his value is 1
            if (a == 0) {
                a = 1
            }

            // Return and generate equation by roots and senior coeff
            return iqeqByRoots(x1, x2, a)
        }

        // Generate equation by difficulty
        fun genIqeqByDiff(diff: String): List<List<Int>> {
            // Initialize the data
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
                    minRoot = -10
                    maxRoot = 10
                    minA = -10
                    maxA = 10
                }

                "reduced_eq_master" -> {
                    minRoot = 1
                    maxRoot = 20
                }

                "reduced_eq_master_2" -> {
                    minRoot = -20
                    maxRoot = 20
                }
            }

            // Generate random equation
            return randomIqeq(minRoot, maxRoot, minA, maxA)
        }

        // Generate equation for growth mode
        fun genIqeqGrowth(solved: Int): List<List<Int>> {
            // Formulas for root range
            var minX = -2 - solved/2
            var maxX = 2 + solved/2

            var minA = 1
            var maxA = 1

            if (solved >= 5) {
                minA = -1 - (solved-5)/5
                maxA = 1 + (solved-5)/5
            }

            return randomIqeq(minX, maxX, minA, maxA)

        }

        // Format equation
        fun format(eq: List<Int>, simpleMode: Boolean = false): String {
            if (simpleMode) {
                return "${eq[0]}   ${eq[1]}   ${eq[2]}"
            }
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

    // Generate a custom equation
    fun genCustom(): List<List<Int>> {
        return randomIqeq(minX, maxX, minA, maxA)
    }

    // Next level event
    fun next(userRoots: List<Int> = emptyList()): Int {
        // If next() doesn't called after IntEq initialization and user roots are incorrect and empty:
        // Return -1 (error)
        if (solved != -1 && userRoots != roots.sorted() && userRoots != emptyList<Int>()) {
            return -1
        }

        // List of equation's coeffs & constant and roots
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
        text = format(eq, simpleMode)

        solved++

        return 0
    }
}