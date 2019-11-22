package com.hwgo.kotlinstudy.kotlininterface

/**

 * @Author wangbin

 * @Date 2019-07-22 09:48

 */
class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }


    override fun bar() {
        super<B>.bar()
    }

}