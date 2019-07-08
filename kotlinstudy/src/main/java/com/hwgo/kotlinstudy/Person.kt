package com.hwgo.kotlinstudy

/**

 * @Author wangbin

 * @Date 2019-07-05 15:54

 */
class Person constructor(firstName: String) {
    val firstProperty = "fist name is $firstName"
    val customKey = firstName.toUpperCase();

    init {
        print("first init block that print$firstName")
    }

    val secondProperty = "second name is$firstName"

    init {
        print("second init block that print$firstName")
    }

    fun mian() {
        Person("hello")
        Person2("hello2")
    }
}

class Person2(val firstName: String) {

}

