package com.hwgo.kotlinstudy.extend

/**

 * @Author wangbin

 * @Date 2019-07-22 10:03

 */
class Extend {
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        var temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }

    fun <T> MutableList<T>.swap2(index1: Int, index2: Int) {
        var temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }

    fun swap() {
        val list = mutableListOf(1, 2, 3)
        list.swap2(0, 2)
    }
}