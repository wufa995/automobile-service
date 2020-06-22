/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.service.impl;

import cn.wufa995.automobile.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wufa995.web.repository.BaseRepository;
import cn.wufa995.web.service.impl.BaseServiceImpl;
import cn.wufa995.automobile.repository.ScheduleRepository;
import cn.wufa995.automobile.service.ScheduleService;
import cn.wufa995.automobile.entity.Schedule;
import cn.wufa995.automobile.entity.Process;
import java.util.List;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Service
public class ScheduleServiceImpl extends BaseServiceImpl<Schedule, String> implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ProcessRepository processRepository;

    @Override
    public BaseRepository getRepository() {
        return scheduleRepository;
    }

    @Override
    public List<Schedule> queryUpdaterByDate(String queryDate) {
        return this.scheduleRepository.queryUpdaterByDate(queryDate);
    }

    @Override
    public List<Process> queryUpdaterApply(String id, String type) {
        return this.processRepository.queryByUpdater(id, type);
    }
}