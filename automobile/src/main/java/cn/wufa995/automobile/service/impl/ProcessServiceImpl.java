/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.service.impl;

import cn.wufa995.common.util.DateFormatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wufa995.web.repository.BaseRepository;
import cn.wufa995.web.service.impl.BaseServiceImpl;
import cn.wufa995.automobile.repository.ProcessRepository;
import cn.wufa995.automobile.service.ProcessService;
import cn.wufa995.automobile.entity.Process;

import java.util.*;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Service
public class ProcessServiceImpl extends BaseServiceImpl<Process, String> implements ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Override
    public BaseRepository getRepository() {
        return processRepository;
    }

    @Override
    public List<String> processTimeUp() {
        List<String> list = new ArrayList<>();
        Calendar calendar =Calendar.getInstance();
        for(int i = 0; i < 4; i++) {
            calendar.add(Calendar.DATE, -1);
            Process process = Process.builder().isDelete(0).type(1).build();
            Map<String, String> like = new HashMap<>();
            like.put("createDate", DateFormatHelper.format("yyyy-MM-dd",calendar.getTime()));
            process.setLikes(like);
            List<Process> processes = this.processRepository.findAllWithResult(process);
            processes.forEach(p -> {
                list.add(p.getId());
                p.setType(4);
                this.processRepository.updateByPrimaryKeySelective(p);
            });
        }
        return list;
    }
}