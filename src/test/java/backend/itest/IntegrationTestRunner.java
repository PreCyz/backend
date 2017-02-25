package backend.itest;

import org.springframework.context.ApplicationContext;

import backend.jpa.impl.JpaRepository;

public class IntegrationTestRunner {
	
	private final ApplicationContext applicationContext;

	public IntegrationTestRunner(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void run() throws Exception {
		crudJPATestIT().executeTests();
	}
	
	private CrudJPATestIT crudJPATestIT() {
		return new CrudJPATestIT(applicationContext.getBean(JpaRepository.class));
	}

}
