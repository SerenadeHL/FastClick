package com.serenadehl.fastclick

import android.util.ArrayMap
import android.util.SparseArray
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2020-05-24 22:30:31
 */
@Aspect
internal class FastClickAspect {
    /**
     * 存储方法的上次点击时间
     *  eg:  key = hashcode
     *       value = "public void com.serenadehl.fastclickexample.MainActivity$onCreate$1.onClick(android.view.View)" to 1590338672000
     */
    private val hashcodeToMethodClickTimeMap = SparseArray<ArrayMap<String, Long>>()

    /**
     * 定义切点，标记切点为所有被@FastClick注解的方法
     */
    @Pointcut("execution(@com.serenadehl.fastclick.FastClick * *(..))")
    fun methodAnnotated() {
    }

    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun filterClick(joinPoint: ProceedingJoinPoint) {
        val methodSignature = joinPoint.signature as? MethodSignature ?: return
        val method = methodSignature.method
        val fastClick = method.getAnnotation(FastClick::class.java)
        val limit = fastClick.limit
        val methodFullName = method.toString()
        val hashcode = joinPoint.target.hashCode()
        val methodClickTimeMap = hashcodeToMethodClickTimeMap.let {
            if (it[hashcode] == null) {
                it.put(hashcode, ArrayMap())
            }
            return@let it[hashcode]
        }
        val lastClickTime = methodClickTimeMap[methodFullName] ?: 0
        val thisClickTime = System.currentTimeMillis()
        if (thisClickTime - lastClickTime > limit) {
            methodClickTimeMap[methodFullName] = thisClickTime
            joinPoint.proceed()
        }
    }
}