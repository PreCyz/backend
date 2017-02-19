package backend.monitor;

import java.io.Serializable;

import backend.dto.AccidentDetails;
import backend.exception.MonitorException;

public interface SubsystemMonitor extends Serializable {

	void execute() throws MonitorException;

	boolean isWorking();

	AccidentDetails accidentDetails();
	
	boolean isEnabled();
	
	void informAboutAccident();

}