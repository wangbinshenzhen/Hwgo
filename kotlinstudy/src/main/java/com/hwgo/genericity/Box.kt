package com.hwgo.genericity

/**

 * @Author wangbin

 * @Date 2019-07-22 10:42

 */
class Box<T>(t: T) {
    var value = t
    fun test() {
        Box<Int>(1).value

        sort(listOf<Int>(1, 2, 3))
    }

    fun <T : Comparable<T>> sort(list: List<T>) {

    }
}