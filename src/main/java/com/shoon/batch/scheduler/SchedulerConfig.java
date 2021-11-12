package com.shoon.batch.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Slf4j
@EnableScheduling
@Component
public class SchedulerConfig {
    private final int POOL_SIZE = 1;
    private ThreadPoolTaskScheduler scheduler;
    
    public boolean stopScheduler() {
        boolean result = true;
        try {
            scheduler.shutdown();
        } catch (Exception e) {
            log.info("Scheduler is not running..");
            result = false;
        }
        
        return result;
        //scheduler.destroy();
        //scheduler.getScheduledThreadPoolExecutor().getActiveCount();
    }
    
    public void startScheduler(String cronExpression, Runnable startRealEstateTradeJob) {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("schduler-task-pool-");
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();

        // 스케쥴러가 시작되는 부분 
        scheduler.schedule(startRealEstateTradeJob, getTrigger(cronExpression));
    }
 
    private Trigger getTrigger(String cronExpression) {
        // 작업 주기 설정 
        return new CronTrigger(cronExpression);
    }

    public long getActiveCount() {
        return scheduler.getActiveCount();
    }
}