package com.kalacheng.util.utils.aop;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.kalacheng.util.utils.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
public class NullPointerAspect {

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Around("execution(@com.kalacheng.library.utils.aop.NullPointerCheck * *(..))")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获得参数类型
        final Parameter[] parameters = method.getParameters();
        //参数值
        final Object[] args = joinPoint.getArgs();
        //参数名称
        String[] names = signature.getParameterNames();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object annotation = parameter.getAnnotation(NotNull.class);
            //含有不为空的注解的参数
            if (null != annotation) {
                if (null == args[i]) {
                    ToastUtil.show(String.format("参数:%s,不能为空", names[i]));
                    return;
                }
            }
        }
        joinPoint.proceed();
    }

}
