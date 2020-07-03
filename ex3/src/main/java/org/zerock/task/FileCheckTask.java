package org.zerock.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	//1분간격으로 스케줄링
	@Scheduled(cron="0 0 2 * * *")
	public void checkFiles () throws Exception{
		log.warn("file check task run...");
		log.warn("----------------------");
	}
}
