package com.shoon.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@Slf4j
public class HouseTradeStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTime = sf.format(stepExecution.getStartTime());
        log.info("[" + startTime + "] " + stepExecution.getStepName() + " 시작...");
    }
 
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info(stepExecution.getStepName() + " 완료...");
        return ExitStatus.COMPLETED;
    }
}