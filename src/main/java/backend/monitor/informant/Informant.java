package backend.monitor.informant;

import backend.dto.AccidentDetails;
import backend.exception.MonitorException;

public interface Informant {

	void inform(AccidentDetails accidentDetails);
	
	void inform(MonitorException monitorException);

}
