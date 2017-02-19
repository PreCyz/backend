package backend.dto;

import java.time.LocalDateTime;

import backend.exception.MonitorException;

public class AccidentDetails {
	
	private LocalDateTime accidentTime;
	private MonitorException monitorException;
	
	public AccidentDetails(LocalDateTime accidentTime, MonitorException monitorException) {
		this.accidentTime = accidentTime;
		this.monitorException = monitorException;
	}
	public LocalDateTime getAccidentTime() {
		return accidentTime;
	}
	public void setAccidentTime(LocalDateTime accidentTime) {
		this.accidentTime = accidentTime;
	}
	public MonitorException getMonitorException() {
		return monitorException;
	}
	public void setMonitorException(MonitorException monitorException) {
		this.monitorException = monitorException;
	}
	
}
