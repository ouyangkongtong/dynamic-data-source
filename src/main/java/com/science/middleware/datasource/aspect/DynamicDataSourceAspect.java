package com.science.middleware.datasource.aspect;

import com.science.middleware.datasource.DataSourceContextHolder;
import com.science.middleware.datasource.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @author kongtong.ouyang on 2017/9/18.
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    @After("execution(* *.*(..))")
    public Object getTargetDataSource(ProceedingJoinPoint point) throws Throwable {
        Object target = point.getTarget();

        MethodSignature signature = (MethodSignature) point.getSignature();

        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if (dataSource == null) {
            target.getClass().getAnnotation(DataSource.class);
            AnnotationUtils.findAnnotation(target.getClass(), DataSource.class);
        }
        if (dataSource != null) {
            DataSourceContextHolder.set(dataSource.value());
        }
        Object result;
        try {
            result = point.proceed();
        } finally {
            DataSourceContextHolder.remove();
        }
        return result;
    }

}
