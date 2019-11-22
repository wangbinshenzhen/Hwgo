package com.hwgo.nestclass

import android.net.wifi.p2p.WifiP2pManager

/**

 * @Author wangbin

 * @Date 2019-07-22 14:15

 */
class Outer {
    private var bar: Int = 1

    class Nested {
        fun foo() = 2
    }

    inner class Inner {
        fun foo() = bar
    }

}

val demo = Outer.Nested().foo()
val demo2=Outer().Inner().foo()
val listener=WifiP2pManager.PeerListListener{}
