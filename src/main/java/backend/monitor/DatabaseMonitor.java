package backend.monitor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import backend.dto.AccidentDetails;
import backend.exception.MonitorException;

public abstract class DatabaseMonitor implements SubsystemMonitor {

	private static final long serialVersionUID = -6651904766637909739L;

	private boolean working;
	private boolean enabled;
	private AccidentDetails accidentDetails;
	
	protected final DataSource dataSource;
	
	public DatabaseMonitor(boolean enabled, DataSource dataSource) {
		this.dataSource = dataSource;
		this.enabled = enabled;
	}
	
	@Override
	public void execute() throws MonitorException {
		if (!enabled) {
			return;
		}
		ResultSet rs = null;
		Statement stmt = null;
		try (Connection connection = dataSource.getConnection()) {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(createQuery());
			if (!rs.first()) {
				working = false;
				
				String message = String.format("Empty result for testing %s query.", this.getClass().getSimpleName());
				System.out.println(message);
				MonitorException monitorException = new MonitorException(message, null);
				accidentDetails = new AccidentDetails(LocalDateTime.now(), monitorException);
				throw monitorException;
			}
			working = true;
			System.out.println("Database mysql is up and running");
			connection.close();
		} catch (SQLException ex) {
			throw new MonitorException(ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {}
		}
	}

	protected abstract String createQuery();

	@Override
	public boolean isWorking() {
		return working;
	}

	@Override
	public AccidentDetails accidentDetails() {
		return accidentDetails;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
