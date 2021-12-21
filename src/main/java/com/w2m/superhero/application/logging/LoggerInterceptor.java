package com.w2m.superhero.application.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Interceptor for logging method measure data based on AOP capability
 */
@Aspect
@Component
public class LoggerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

  /**
   * logging of method time execution
   * @param point
   * @return
   * @throws Throwable
   */
  @Around("execution(* *(..)) && @annotation(Timing)")
  public Object log(ProceedingJoinPoint point) throws Throwable {
    long initTime = System.currentTimeMillis();
    Object result = point.proceed();
    long finishTime = System.currentTimeMillis();
    long durationTime = finishTime - initTime;
    MethodSignature methodSignature = MethodSignature.class.cast(point.getSignature());
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getMethod().getName();
    logger.info("classMethod = {}.{}, durationTime = {}ms",
        new Object[]{className, methodName, durationTime});
    return result;
  }
}
