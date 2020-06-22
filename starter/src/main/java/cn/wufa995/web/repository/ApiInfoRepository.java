/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/03/25 20:00
 */
package cn.wufa995.web.repository;


import cn.wufa995.web.entity.ApiInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ApiInfoRepository{

    ApiInfo findOneByClassReferenceAndMethodName(@Param("classReference") String classReference, @Param("methodName") String methodName);

    ApiInfo selectById(String id);

    Integer insert(ApiInfo apiInfo);

    Integer deleteAll();

    Integer deleteById(String id);

    Integer updateIsFinishById(@Param("id") String id, @Param("isFinish") Integer isFinish);
}
