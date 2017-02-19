package backend.initiator;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class LoggerInitiator {

	protected Log logger;

	@PostConstruct
	public void init() {
		logger = LogFactory.getLog(this.getClass());
	}
}
