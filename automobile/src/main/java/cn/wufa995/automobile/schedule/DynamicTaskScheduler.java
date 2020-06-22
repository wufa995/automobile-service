package cn.wufa995.automobile.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Profile("dev")
@Scope(value = "singleton")
@Component
@EnableScheduling
public class DynamicTaskScheduler {

    private Map<String, ScheduledFuture<?>> cache = new ConcurrentHashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void startCron(Runnable task, String cron, String taskName) {
        if(cache.containsKey(taskName)) {
            stopCron(taskName);
        }
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(
                task, new CronTrigger(cron)
        );
        cache.put(taskName,future);
    }

    public void stopCron(String taskName) {
        ScheduledFuture<?> future = this.cache.get(taskName);
        if (future != null) {
            future.cancel(true);
            log.info("定时任务:{} 被关闭", taskName);
            this.cache.remove(taskName);
        }
    }
}
