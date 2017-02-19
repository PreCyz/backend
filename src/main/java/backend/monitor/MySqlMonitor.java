package backend.monitor;

import javax.sql.DataSource;

import backend.informant.Informant;

public class MySqlMonitor extends DatabaseMonitor {

	private static final long serialVersionUID = 8342482791401248347L;
	
	private Informant informant;

	public MySqlMonitor(boolean enabled, DataSource dataSource) {
		super(enabled, dataSource);
	}

	public void setInformant(Informant informant) {
		this.informant = informant;
	}

	@Override
	protected String createQuery() {
		return "select 1";
	}

	@Override
	public void informAboutAccident() {
		informant.inform(accidentDetails());
	}

}
