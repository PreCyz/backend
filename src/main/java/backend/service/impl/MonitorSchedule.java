package backend.service.impl;

import org.springframework.scheduling.annotation.Scheduled;

import backend.exception.MonitorException;
import backend.monitor.SubsystemMonitor;
import backend.monitor.executor.MonitorExecutor;
import backend.monitor.informant.Informant;

public class MonitorSchedule {
	
	private final MonitorExecutor monitorExecutor;
	private SubsystemMonitor mysqlMonitor;
	private Informant informant;
	
	public MonitorSchedule(MonitorExecutor monitorExecutor) {
		this.monitorExecutor = monitorExecutor;
	}

	public void setMysqlMonitor(SubsystemMonitor mysqlMonitor) {
		this.mysqlMonitor = mysqlMonitor;
		monitorExecutor.addMonitor(mysqlMonitor);
	}

	public void setInformant(Informant informant) {
		this.informant = informant;
	}

	@Scheduled(cron="0 0/1 * * * ?" ) //run every 1 minute
	public void mysqlMonitorRunner() {
		try {
			mysqlMonitor.execute();
		} catch (MonitorException e) {
			informant.inform(e);
		}
	}
	
	@Scheduled(cron="0 0/5 * * * ?" ) //run every 5 minute
	public void monitorsChecker() {
		monitorExecutor.checkMonitors();
	}

}
