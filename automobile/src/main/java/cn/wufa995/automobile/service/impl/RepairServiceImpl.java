/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wufa995.web.repository.BaseRepository;
import cn.wufa995.web.service.impl.BaseServiceImpl;
import cn.wufa995.automobile.repository.RepairRepository;
import cn.wufa995.automobile.service.RepairService;
import cn.wufa995.automobile.entity.Repair;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Service
public class RepairServiceImpl extends BaseServiceImpl<Repair, String> implements RepairService {

    @Autowired
    private RepairRepository repairRepository;

    @Override
    public BaseRepository getRepository() {
        return repairRepository;
    }
}