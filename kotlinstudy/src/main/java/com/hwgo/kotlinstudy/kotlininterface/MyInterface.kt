package com.hwgo.kotlinstudy.kotlininterface

/**

 * @Author wangbin

 * @Date 2019-07-22 09:29

 */
interface MyInterface {
    //抽象的
    val prop: Int
    val propertWithImplementation: String
        get() = "foo"

    fun bar()
    fun boo() {
        print(propertWithImplementation)
    }
}