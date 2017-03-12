package backend.jpa.extractor;

import org.eclipse.persistence.descriptors.ClassExtractor;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

public class ExampleClassExtractor extends ClassExtractor {

	private final String COLUMN_NAME = "DATABASE_COLUMN_NAME";
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class extractClassFromRow(Record row, Session session) {
		row.get(COLUMN_NAME);
		/*
		if(StringHelper.notEmpty(message) && message.startsWith(LogSQLEvent.class.getSimpleName())){
			return LogSQLEvent.class;
		} 
		return LogDBEvent.class;*/
		return this.getClass();
	}

}
