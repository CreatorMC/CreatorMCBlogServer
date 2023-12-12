package com.creator.aspect;

import com.creator.domain.entity.Comment;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SensitiveWordAspect {

    @Autowired
    private SensitiveWordBs sensitiveWordBs;

    @Pointcut("@annotation(com.creator.annotation.SensitiveWordFilter)")
    public void pt() {}

    @Pointcut("@annotation(com.creator.annotation.SensitiveWordInit)")
    public void ptInit() {}

    /**
     * 过滤敏感词
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pt()")
    public Object sensitiveWordFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof  Comment) {
                Comment comment = (Comment)args[i];
                comment.setContent(sensitiveWordBs.replace(comment.getContent()));
                args[i] = comment;
            }
        }
        return joinPoint.proceed(args);
    }

    /**
     * 重新加载敏感词
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("ptInit()")
    public Object sensitiveWordInit(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        sensitiveWordBs.init();
        return result;
    }
}
