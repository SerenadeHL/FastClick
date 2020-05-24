package com.serenadehl.fastclick

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2020-05-24 22:29:17
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FastClick(val limit: Long = 500)