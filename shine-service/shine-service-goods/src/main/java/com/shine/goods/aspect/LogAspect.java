package com.shine.goods.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc: 日志切面
 * ----------------------------
 * @Author: shine
 * @Date: 2020/1/9 15:48
 * ----------------------------
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /** 以 controller 包下定义的所有请求为切入点 */
    @Pointcut("execution( * com.shine.*.controller.*.*(..))")
    public void webLog() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 打印请求相关参数
        logger.info("========================================== Start ==========================================");
        // 打印请求 url
        logger.info("响应地址: {}", request.getRequestURL().toString());
        // 打印 Http method
        logger.info("请求方式: {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        logger.info("请求方法: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        logger.info("请求地址: {}", request.getRemoteAddr());
        // 打印请求入参
        logger.info("请求参数: {}", new Gson().toJson(joinPoint.getArgs()));
    }

    /**
     * 在切点之后织入
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        logger.info("=========================================== End ===========================================");
        // 每个请求之间空一行
        logger.info("");
    }

    /**
     * 环绕
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ServerResponse = proceedingJoinPoint.proceed();
        // 打印出参
        logger.info("返回参数: {}", new Gson().toJson(ServerResponse));
        // 执行耗时
        logger.info("总共耗时: {} ms", System.currentTimeMillis() - startTime);
        return ServerResponse;
    }

}
