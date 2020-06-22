/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.service;

import cn.wufa995.automobile.entity.Process;
import cn.wufa995.web.service.BaseService;
import cn.wufa995.automobile.entity.Schedule;

import java.util.List;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
public interface ScheduleService extends BaseService<Schedule, String> {

    List<Schedule> queryUpdaterByDate(String queryDate);

    List<Process> queryUpdaterApply(String id, String type);
}