package cn.wufa995.common.annotation;


import cn.wufa995.common.enums.ProjectNameEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiDescribe {

    String describe() default "";

    boolean isUse () default true;

    ProjectNameEnum projectName () default ProjectNameEnum.ALL;

}
