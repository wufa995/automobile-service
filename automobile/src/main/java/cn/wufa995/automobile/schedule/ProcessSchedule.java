/**
 * @author: qinbo[qin_bo@suixingpay.com]
 * @date: 2019年03月11日 15时57分阅，禁止外泄以及用于其他的商业用途。
 */
package cn.wufa995.automobile.schedule;

import cn.wufa995.automobile.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessSchedule {

    @Autowired
    private ProcessService processService;

    /**
     * 每天凌晨0点开始同步
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void init() {
        this.processService.processTimeUp();
    }
}