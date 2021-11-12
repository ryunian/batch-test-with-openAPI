package com.shoon.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

// "0 0 * * * *" = the top of every hour of every day.
// "*/10 * * * * *" = 매 10초마다 실행한다.
// "0 0 8-10 * * *" = 매일 8, 9, 10시에 실행한다
// "0 0 6,19 * * *" = 매일 오전 6시, 오후 7시에 실행한다.
// "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
// "0 0 9-17 * * MON-FRI" = 오전 9시부터 오후 5시까지 주중(월~금)에 실행한다.
// "0 0 0 25 12 ?" = every Christmas Day at midnight

@Component
@RequiredArgsConstructor
@Slf4j
public class RunScheduler {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Scheduled(fixedDelay = 5 * 10000L)
    public void performRealEstateTradeJob() throws Exception {
        log.info("스케쥴러 시작");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String currentMonth = simpleDateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .addString("month", currentMonth)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}