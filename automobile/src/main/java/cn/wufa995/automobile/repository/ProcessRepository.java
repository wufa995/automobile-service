/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.repository;

import cn.wufa995.web.repository.BaseRepository;
import cn.wufa995.automobile.entity.Process;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Mapper
@Repository
public interface ProcessRepository extends BaseRepository<Process, String> {

    List<Process> queryByUpdater(@Param("id") String id, @Param("type")String type);
}
