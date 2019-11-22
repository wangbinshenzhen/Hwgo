package com.hwgo.enumclass

/**

 * @Author wangbin

 * @Date 2019-07-22 14:20

 */
enum class Direction {
    NORTH, SOUTH, WEST, EAST

}

enum class Color(val rgb: Int) {
    RED(0xff0000),
    GREEN(0x00ff00),
    BLU(0x0000ff),
}

enum class ProtocolState {
    WARING {
        override fun signal() = TALKING
    },
    TALKING {
        override fun signal() = WARING
    };

    abstract fun signal(): ProtocolState
}