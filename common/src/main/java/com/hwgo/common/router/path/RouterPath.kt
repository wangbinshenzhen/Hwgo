package com.hwgo.common.router.path

/**
 * ClassName: Router path
 * Description: 全局的路由path配置
 *
 *
 * Author: wangbin
 * Date: 2019/06/06 03:26:24
 */
object RouterPath {
    class Main {
        companion object {
            const val MainActivity = "/app/main"
        }
    }

    class Flutter {
        companion object {
            const val CommonFlutterActivity = "/flutter/common"
        }
    }

    class Demo {
        companion object {
            const val ClientActivity = "/demo/ClientActivity"
            const val ThreadDemoActivity = "/demo/ThreadDemoActivity"
            const val CoordinatorLayoutActivity = "/demo/CoordinatorLayoutActivity"
        }
    }
    class Study{
        companion object{
            const val  RxStudyActivity="/rxjavastudy/RxStudyActivity"
        }
    }
}
