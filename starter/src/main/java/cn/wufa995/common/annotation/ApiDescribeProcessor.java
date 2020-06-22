package cn.wufa995.common.annotation;

import cn.wufa995.common.enums.ProjectNameEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Component
public class ApiDescribeProcessor implements BeanPostProcessor {

    public static Map<String, String> API_DESCRIBE_MAP = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                ApiDescribe annotation = AnnotationUtils.findAnnotation(method, ApiDescribe.class);
                if (annotation != null) {
                    String describe = annotation.describe();
                    boolean isUse = annotation.isUse();
                    ProjectNameEnum projectNameEnum = annotation.projectName();
                    if(isUse && projectNameEnum.equals(ProjectNameEnum.ALL)) {
                        API_DESCRIBE_MAP.put(method.getDeclaringClass().toString().substring(6) + "." + method.getName(), describe);
                    }
                }
            }
        }
        return bean;
    }
}
