package com.serenadehl.fastclick

import android.util.ArrayMap
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
class FastClickAspect {
    /**
     * 存储方法的上次点击时间
     * eg:  key = "public void com.serenadehl.fastclickexample.MainActivity$onCreate$1.onClick(android.view.View)"
     *      value = 1590338672000
     */
    private val storedClickTimes = ArrayMap<String, Long>()

    /**
     * 定义切点，标记切点为所有被@FastClick注解的方法
     */
    @Pointcut("execution(@com.serenadehl.fastclick.FastClick * *(..))")
    fun methodAnnotated() {
    }

    @Around("methodAnnotated()")
    @Throws(Throwable::class)
    fun filterClick(joinPoint: ProceedingJoinPoint) {
        val methodSignature = joinPoint.signature as? MethodSignature?:return
        val method = methodSignature.method
        val fastClick = method.getAnnotation(FastClick::class.java)
        val limit = fastClick.limit
        val methodFullName = method.toString()
        //public void com.serenadehl.fastclickexample.MainActivity$onCreate$1.onClick(android.view.View)
        val lastClickTime = storedClickTimes[methodFullName] ?: 0
        val thisClickTime = System.currentTimeMillis()
        if (thisClickTime - lastClickTime > limit) {
            storedClickTimes[methodFullName] = thisClickTime
            joinPoint.proceed()
        }
    }
}