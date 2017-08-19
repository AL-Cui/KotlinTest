package com.example.cuiduo.kotlin.domain.network

/**
 * @author wupanjie
 */
interface Source<T> {
    fun obtain(url:String): T
}