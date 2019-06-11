package com.hwgo.kotlinstudy

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class FistKotlin {
    val PI = 3.14
    var x = 0;
    /**
     * 计算两个整数之和返回
     * @param a Int
     * @param b Int
     * @return Int
     */
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    /**
     * 将表达式作为函数体，返回值类型自动推断的函数
     * @param a Int
     * @param b Int
     * @return Int
     */
    fun sum2(a: Int, b: Int) = a + b

    /**
     * 打印两个数之和,函数返回无意义的值
     * @param a Int
     * @param b Int
     * @return Unit
     */
    fun printSum(a: Int, b: Int): Unit {
        System.out.println("sum of $a and $b is ${a + b}")
        System.out.println("sum is ${sum2(a, b)}")
    }

    /**
     *打印两个整数之和，Unit返回类型可以省略
     * @param a Int
     * @param b Int
     */
    fun printSum2(a: Int, b: Int) {
        System.out.println("sum of $a and $b is ${a + b}")
        System.out.println("sum is ${sum2(a, b)}")
    }

    /**
     *定义变量
     */
    fun definVaridate() {
        //定义只读局部变量使用关键字val，只能赋值一次
        val a: Int = 1
        val b = 2
        val c: Int
        c = 3
        //定义可从新赋值的变量用var
        var i: Int = 4
        i += 1
    }

    /**
     * 顶层变量修改
     */
    fun incrementX(): String {
        x += 1
        return "x=$x"
    }

    /**
     * 打印string字符串
     * @param sts String
     */
    private fun println(sts: String) {
        System.out.println(sts)
    }

    /**
     * 使用字符串模板
     */
    fun stringsTemlate(): String {
        var a = 1
        val s1: String = "a is $a"
        a = 2
        return "${s1.replace("is", "was")},but now is $a"
    }

    /**
     * 使用表达式
     */
    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }

    }

    /**
     * 使用if表达式
     */
    fun maxOf2(a: Int, b: Int) = if (a > b) a else b

    /**
     * 返回可能为null的方法
     * @param str String
     * @return Int?
     */
    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    /**
     * null判断
     * @param art1 String
     * @param art2 String
     */
    fun printProduct(art1: String, art2: String) {
        val x = parseInt(art1)
        val y = parseInt(art2)
        if (x != null && y != null) {
            println("${x * y}")
        } else {
            println("either $art1 or $art2 is not a number")
        }
    }

    /**
     * 使用类型检测及自动类型转换
     * @param obj Any
     * @return Int?
     */
    fun getStringlength(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        }
        return null
    }

    /**
     * 使用类型检测及自动类型转换
     * @param obj Any
     * @return Int?
     */
    fun getStringlength2(obj: Any): Int? {
        if (obj !is String) {
            return null
        }
        return obj.length
    }

    /**
     * 使用类型检测及自动类型转换
     * @param obj Any
     * @return Int?
     */
    fun getStringlength3(obj: Any): Int? {

        if (obj is String && obj.length > 0) {
            return obj.length
        }
        return null
    }

    /**
     * 使用for循环
     */
    fun forLoop() {
        val items = listOf("a", "b", "c", "d", "e")
        for (item in items) {
            println(item)
        }
    }

    fun forLoop2() {
        val items = listOf("a", "b", "c", "d", "e")
        for (index in items.indices) {
            println("$index ${items[index]}")
        }
    }

    /**
     * 使用while循环
     */
    fun whileLoop() {
        val items = listOf("a", "b", "c", "d", "e")
        var index = 0
        while (index < items.size) {
            println("$index ${items[index]}")
        }
    }

    /**
     * 使用when
     * @return String
     */
    fun describe(obj: Any): String =
            when (obj) {
                1 -> "one"
                "hello" -> "Greeting"
                is Long -> "Long"
                !is String -> "Not a String"
                else -> "unknown"
            }

    /**
     *使用区间 range
     * 使用 in 运算符来检测某个数字是否在指定区间内：
     */
    fun inRange() {
        val x = 10
        val y = 9
        if (x in x..y + 1) {
            println("fits in range")
        }
    }

    /**
     *使用区间 range
     * 使用 in 运算符来检测某个数字是否在指定区间外：
     */
    fun notInRange() {
        val items = listOf("a", "b", "c")
        if (-1 !in 0..items.lastIndex) {
            println("-1 not in range")
        }
        if (items.size !in items.indices) {
            println("list size is out of valid list indices range ,too")

        }
    }

    /**
     * 区间迭代
     */
    fun rangeIteration() {
        for (x in 1..5) {
            println("$x")
        }
    }

    /**
     * 数列迭代
     */
    fun seriesIteration() {
        for (x in 1..10 step 2) {
            println("$x")
        }
        for (x in 9 downTo 0 step 3) {
            println("$x")
        }
    }

    /**
     * 使用集合
     */
    fun useCollections() {
        val items = listOf("a", "b", "c", "d")
        for (item in items) {
            println(item)
        }
    }

    /**
     * 使用集合
     */
    fun useCollections2() {
        val items = listOf("a", "b", "c", "d")
        when {
            "a" in items -> println("a")
            "b" in items -> println("b")
        }
    }

    /**
     * 使用 lambda 表达式来过滤（filter）与映射（map）集合：
     */
    fun filter() {
        val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
        fruits.filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { println(it) }
    }

    /**
     * map使用 遍历
     */
    fun map() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 3)
        for ((k, v) in map) {
            println("$k----$v")
        }
    }

//    /**
//     * 延迟属性
//     */
//    fun lazy() {
//     var str:String by lazy()
//    }
    /**
     *If not null 缩写
     */
    fun notNull() {
        val files = File("").listFiles()
        println(files?.size)
    }

    /**
     *If not null 缩写
     */
    fun notNullElse() {
        val files = File("").listFiles()
        println(files?.size ?: "empty")
    }

    /**
     *if null 执行一个语句
     */
    fun notNullElse2() {
        val files = File("").listFiles()
        var file = files[0] ?: throw IllegalArgumentException("file is null")
    }

    /**
     *在可能会空的集合中取第一元素
     */
    fun notNullElse3() {
        val files = File("").listFiles()
        var file = files.firstOrNull() ?: ""
    }

    /**
     *if not null 执行代码
     */
    fun notNullElse4() {
        val files = File("").listFiles()
        files?.let {
            // 代码会执行到此处, 假如data不为null
        }
    }

    /**
     *映射可空值（如果非空的话）
     */
    fun notNullElse5() {
        val files = File("").listFiles()
        val files2 = File("").listFiles()
        files?.let {
            //.....
        } ?: files2
    }

    /**
     *返回 when 表达式
     */
    fun transform(color: String): Int {
        return when (color) {
            "red" -> 1
            "green" -> 2
            "blue" -> 3
            else -> throw IllegalArgumentException("Invalid color param value")
        }
    }

    /**
     *“try/catch”表达式
     */
    fun tryCatch(color: String): Int {
        return try {
            sum(5, 6)
        } catch (e: ArithmeticException) {
            throw IllegalArgumentException(e)
        }
    }

    /**
     *“if”表达式
     */
    fun ifTest(param: Int) {
        val result = if (param == 1) {
            "one"
        } else if (param == 2) {
            "two"
        } else {
            "three"
        }
    }

    /**
     *返回类型为 Unit 的方法的 Builder 风格用法
     */
    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }

    /**
     *单表达式函数
     */
    fun theAnswer() = 42

    /**
     *单表达式函数
     */
    fun theAnswer2(): Int {
        return 42
    }

    /**
     * 单表达式函数与其它惯用法一起使用能简化代码，例如和 when 表达式一起使用：
     * @param color String
     * @return Int
     */
    fun transform2(color: String): Int = when (color) {
        "red" -> 1
        "green" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }

    /**
     * 对一个对象实例调用多个方法 （with）
     */
    fun multiFun() {
        val turtle = Turtle()
        with(turtle) {
            penDown()
            penUp()
            for (i in 1..4) {
                penUp()
            }
        }
    }

    /**
     * Java 7 的 try with resources
     */
    fun tryWithResource() {
        var stream = Files.newInputStream(Paths.get(""))
        stream.buffered().reader().use { reader ->
            println(reader.readText())
        }
    }

    /**
     * 交换两个变量
     */
    fun exchange() {
        var a = 1
        var b = 2
        a = b.also { b = a }
    }

    /**
     * 对于需要泛型信息的泛型函数的适宜形式
     */
    //inline fun <reified T:Any > Gson.formatJson(json:JsonElement):T=this.formatJson(json,T::class.java)
}

abstract class Shape(val sides: List<Double>) {
    /**
     * 周长
     */
    val perimeter: Double get() = sides.sum()

    /**
     * 计算面积
     * @return Double 面积
     */
    abstract fun calculateArea(): Double
}

interface RectangleProperties {
    val isSquare: Boolean
}

class Rectangle(var height: Double, var length: Double) : Shape(listOf(height, length, height, length)), RectangleProperties {
    override fun calculateArea(): Double {
        return height * length
    }

    override val isSquare: Boolean
        get() = height == length
}

class Triangle(var sideA: Double,
               var sideB: Double, var sideC: Double) : Shape(listOf(sideA, sideB, sideC)) {
    override fun calculateArea(): Double {
        val s = perimeter / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC))
    }


}

class Turtle {
    fun penDown() {}
    fun penUp() {}

}



