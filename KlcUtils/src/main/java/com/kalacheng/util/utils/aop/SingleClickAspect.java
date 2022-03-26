package com.kalacheng.util.utils.aop;


import android.view.View;

import com.kalacheng.util.utils.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class SingleClickAspect {
    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;

    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 注意：这里me.baron.test.annotation.SingleClick需要替换成
     * 你自己项目中SingleClick这个类的全路径哦
     */
    //@Pointcut("execution(@com.kalacheng.utils.SingleClick * *(..))")
    public void methodAnnotated() {
    }

    /**
     * 定义一个切面方法，包裹切点方法
     */
    //@Around("methodAnnotated()")
    @Around("execution(@com.example.baseutils.utils.SingleClick * *(..))")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的参数
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (view == null) {
            return;
        }

        // 取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        if (!method.isAnnotationPresent(SingleClick.class)) {
            return;
        }
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        // 判断是否快速点击
        if (!isFastDoubleClick(view, singleClick.value())) {
            // 不是快速点击，执行原方法
            joinPoint.proceed();
        } else {
            ToastUtil.show("棒棒棒,你的手速太快了,应该单身了好久吧");
        }
    }

    public boolean isFastDoubleClick(View v, long intervalMillis) {
        int viewId = v.getId();
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return false;
        }
    }

}
