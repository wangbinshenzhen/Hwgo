package com.hwgo.kotlinstudy.kotlininterface

/**

 * @Author wangbin

 * @Date 2019-07-22 09:41

 */
data class Employee(override val firstName: String, override val lastName: String, val position: Int) : Person {

}