/**
 * @author: wufa995[wufa995@qq.com]
 * @date: 2019年03月25日 20时01分
 */
package cn.wufa995;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan({"cn.wufa995.automobile.repository", "cn.wufa995.web.repository"})
public class Application {
    public static void main(String[] args) {
        // 启动SpringBoot应用
        SpringApplication.run(Application.class,args);
    }
}