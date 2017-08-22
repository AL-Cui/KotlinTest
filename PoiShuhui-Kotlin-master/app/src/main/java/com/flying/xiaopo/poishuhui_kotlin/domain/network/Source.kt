package com.flying.xiaopo.poishuhui_kotlin.domain.network

/**
 * @author wupanjie
 */
interface Source<T> {
    fun obtain(url:String): T
}