package backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
	AopConfig.class, 
	CxfConfig.class, 
	DatabaseConfig.class,
	JpaConfig.class,
	//LoggingConfig.class,
	RepositoryConfig.class, 
	ScheduleConfig.class, 
	ServiceConfig.class, 
})
public class CoreConfig {
	
}
