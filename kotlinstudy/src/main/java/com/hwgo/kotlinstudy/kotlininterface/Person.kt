package com.hwgo.kotlinstudy.kotlininterface

/**

 * @Author wangbin

 * @Date 2019-07-22 09:39

 */
interface Person : Named {
    val firstName: String
    val lastName: String
    override val name: String
        get() = "$firstName,$lastName"
}