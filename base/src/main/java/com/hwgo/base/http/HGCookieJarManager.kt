package com.hwgo.base.http

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

/**
 *CookieJar管理类，负责CookieJar的创建和清除
 * @Author wangbin
 * @Date 2019-07-09 17:36
 */
class CTCookieJarManager private constructor() {

    private object SingletonHolder {
        val holder: PersistentCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(CTNetworkInitHelper.application))
    }

    companion object {
        val cookieJar = SingletonHolder.holder
        fun clear() {
            cookieJar.clear()
        }
    }


}