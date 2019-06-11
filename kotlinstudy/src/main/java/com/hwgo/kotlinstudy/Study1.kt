package com.hwgo.kotlinstudy

/**

 * @Author wangbin

 * @Date 2019-06-10 20:01

 */
class Study1 {
    
    fun show1() {
        val a: Int = 10000
        print(a === a)//输出“true”
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        print(boxedA === anotherBoxedA)//输出“false”
    }

    fun show2() {
        val a: Int = 10000
        print(a == a)//输出“true”
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        print(boxedA == anotherBoxedA)//输出“true”
    }
}