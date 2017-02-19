package backend.jpa.entities.log;

import javax.persistence.Entity;

import org.eclipse.persistence.annotations.Customizer;
import org.slf4j.helpers.MessageFormatter;

import backend.jpa.customizer.LogSQLEventCustomizer;

@Entity
@Customizer(LogSQLEventCustomizer.class)
public class LogSQLEvent extends LogEvent {
	private static final long serialVersionUID = 1L;

    public LogSQLEvent(){}
	
	/**
     * @param sqlStatement any SQL statement
     */
    public LogSQLEvent(String sqlStatement) {
    	super();
    	messageBase = sqlStatement;
    }
    /**
     * @param sqlStatement any SQL statement
     * @param parameters SQL statement parameters
     */
    public LogSQLEvent(String sqlStatement, String ... parameters) {
    	this( String.format("%s - [%s]", sqlStatement, LogSQLEvent.class.getSimpleName()) );
    	this.messageParams = parameters;
    }

    @Override
    public String toString() {
    	if (messageParams == null) {
			return messageBase;
		} else {
			return MessageFormatter.arrayFormat(messageBase.replaceAll("\\?", "{}"), messageParams).getMessage();
		}
    }

	public void setLevel(String string) {
		// TODO Auto-generated method stub
		
	}
}
