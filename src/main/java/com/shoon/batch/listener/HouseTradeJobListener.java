package com.shoon.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HouseTradeJobListener implements JobExecutionListener {
    public void beforeJob(JobExecution jobExecution) {
        String param = jobExecution.getJobParameters().getString("month");
        log.info(param + " - 국토부 전국 부동산 실거래가 데이터 수집 Job 시작");
    }
 
    public void afterJob(JobExecution jobExecution) {
        String exitCode = jobExecution.getExitStatus().getExitCode();
        
        if (exitCode.equals(ExitStatus.COMPLETED.getExitCode())) {
            log.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 처리 완료.");
        } else if (exitCode.equals(ExitStatus.FAILED.getExitCode())) {
            log.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 처리 오류 발생.");
        } else if (exitCode.equals(ExitStatus.STOPPED.getExitCode())) {
            log.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 정지.");
        } else {
            log.info("JobID [" + jobExecution.getJobId() + "] 국토부 전국 부동산 실거래가 데이터 수집 Job 처리가 정상적으로 완료되지 않음.");
        }
    }
}