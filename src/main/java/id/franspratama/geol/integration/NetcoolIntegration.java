package id.franspratama.geol.integration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import id.franspratama.geol.task.Task;

@Component("netcoolIntegration")
public class NetcoolIntegration implements Task{

	@Override
	@Scheduled(cron="15 15 * * * *")
	public void doTask() {
		
	}

}
