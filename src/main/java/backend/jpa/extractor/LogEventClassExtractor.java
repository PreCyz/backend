package backend.jpa.extractor;

import org.eclipse.persistence.descriptors.ClassExtractor;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

import backend.helper.StringHelper;
import backend.jpa.entity.log.LogDBEvent;
import backend.jpa.entity.log.LogSQLEvent;

public class LogEventClassExtractor extends ClassExtractor {

	private final String COLUMN_NAME = "MESSAGE";
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class extractClassFromRow(Record row, Session session) {
		String message = (String) row.get(COLUMN_NAME);
		if(StringHelper.notEmpty(message) && message.startsWith(LogSQLEvent.class.getSimpleName())){
			return LogSQLEvent.class;
		} 
		return LogDBEvent.class;
	}

}
