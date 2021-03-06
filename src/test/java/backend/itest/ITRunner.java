package backend.itest;

import org.springframework.context.ApplicationContext;

import backend.itest.impl.CrudJpaIT;
import backend.jpa.impl.JpaRepository;
import backend.service.SessionService;

public class ITRunner {
	
	private final ApplicationContext applicationContext;

	public ITRunner(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void run() throws Exception {
		crudJPATestIT().run();
	}
	
	private CrudJpaIT crudJPATestIT() {
		return new CrudJpaIT(
				applicationContext.getBean(JpaRepository.class),
				applicationContext.getBean(SessionService.class)
			);
	}

}
