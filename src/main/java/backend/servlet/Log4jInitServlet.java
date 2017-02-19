package backend.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitServlet extends HttpServlet {

	private static final long serialVersionUID = -6214396464804601522L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		Properties prop = new Properties();
		try {
			prop.load(Log4jInitServlet.class.getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			return;
		}
		String file = config.getServletContext().getInitParameter("log4jFileAppenderFILE");
		if (file != null && file.trim().length() > 0) {
			String level = config.getServletContext().getInitParameter("log4jFileAppenderLEVEL");
			if (level == null || level.trim().length() == 0) {
				level = "INFO";
			}
			
			prop.setProperty("log4j.logger.backend", level + ", LOGFILE");
			prop.setProperty("log4j.appender.LOGFILE.File", file);
			
			String emailErrors = config.getServletContext().getInitParameter("emailErrors");
			if ("true".equals(emailErrors)) {
				prop.setProperty("log4j.rootLogger", prop.getProperty("log4j.rootLogger") + ", MAIL");
			}
		}
		PropertyConfigurator.configure(prop);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}
}
