/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.repository;

import cn.wufa995.web.repository.BaseRepository;
import cn.wufa995.automobile.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
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
public interface ScheduleRepository extends BaseRepository<Schedule, String> {

    List<Schedule> queryUpdaterByDate(String queryDate);
}
