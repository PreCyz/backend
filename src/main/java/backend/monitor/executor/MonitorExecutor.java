package backend.monitor.executor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import backend.monitor.SubsystemMonitor;
import backend.monitor.informant.Informant;

public class MonitorExecutor implements Serializable {

	private static final long serialVersionUID = -5605323484688202516L;

	private final boolean enabled;
	private List<SubsystemMonitor> monitors = new ArrayList<>();
	private Informant informant;

	public MonitorExecutor(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void addMonitor(SubsystemMonitor monitor) {
		monitors.add(monitor);
	}

	public void checkMonitors() {
		if(!enabled || monitors.isEmpty()) {
			return;
		}
		monitors.stream().forEach(monitor -> {
			if (monitor.isEnabled() && !monitor.isWorking()) {
				informant.inform(monitor.accidentDetails());
			}
		});
	}
}
