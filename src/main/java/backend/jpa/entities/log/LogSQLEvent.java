package backend.jpa.entities.log;


import org.eclipse.persistence.annotations.Customizer;

import backend.jpa.customizer.LogSQLEventCustomizer;

@Customizer(LogSQLEventCustomizer.class)
public class LogSQLEvent {
	private static final long serialVersionUID = 1L;

    public LogSQLEvent(){}
	
}
