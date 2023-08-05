package com.creator.aspect;

import com.alibaba.fastjson.JSON;
import com.creator.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.creator.annotation.SystemLog)")
    public void pt() {}

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            handleBefore(joinPoint);
            result = joinPoint.proceed();
            handleAfter(result);
        } finally {
            //结束后换行 拼接系统换行符
            log.info("=======End=======" + System.lineSeparator());
        }
        return result;
    }

    private void handleAfter(Object result) {
        log.info("Response        : {}", JSON.toJSONString(result));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        //获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        //打印信息
        log.info("=======Start=======");
        log.info("URL            : {}", request.getRequestURL());
        log.info("Business       : {}", systemLog.businessName());
        log.info("HTTP Method    : {}", request.getMethod());
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("IP             : {}", request.getRemoteHost());
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof MultipartFile) {
                //如果这个对象是文件的话，以文件名代替
                args[i] = ((MultipartFile)args[i]).getOriginalFilename();
            }
        }
        log.info("Request Args   : {}", JSON.toJSONString(args));
    }

    /**
     * 获取SystemLog注解对象
     * @param joinPoint
     * @return
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }
}
