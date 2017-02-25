package backend.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import backend.facade.RestFacade;
import backend.facade.WebServiceFacade;
import backend.facade.impl.RestFacadeImpl;
import backend.facade.impl.WebServiceFacadeImpl;
import backend.interceptor.ErrorHandlerInterceptor;
import backend.mapper.DAOExceptionMapper;

@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml"})
public class CxfConfig {
	
	@Autowired
	private Bus cxfBus;
	
	@Autowired
	private ServiceConfig serviceConfig;
	
	@Bean(destroyMethod = "shutdown")
	public SpringBus springBus() {
		setupCxfBus();
		return (SpringBus) cxfBus;
	}
	
	private void setupCxfBus() {
		cxfBus.getInInterceptors().add(new LoggingInInterceptor());
		cxfBus.getOutInterceptors().add(new LoggingOutInterceptor());
	}
	
	@Bean
	public WebServiceFacade webServiceFacade() {
		WebServiceFacadeImpl webServiceFacadeImpl = new WebServiceFacadeImpl();
		webServiceFacadeImpl.setAuthenticationService(serviceConfig.authenticationService());
		return webServiceFacadeImpl;
	}
	
	@Bean
	@DependsOn("springBus")
	public Endpoint wsFacade() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), webServiceFacade());
		endpoint.setAddress("/WebService");
		endpoint.publish();
		return endpoint;
	}
	
	//REST config start
	@Bean
	public RestFacade restFacade() {
		RestFacadeImpl restFacadeImpl = new RestFacadeImpl();
		restFacadeImpl.setAuthenticationService(serviceConfig.authenticationService());
		return restFacadeImpl;
	}
	
	@Bean
	public ErrorHandlerInterceptor errorHandlerInterceptor() {
		return new ErrorHandlerInterceptor(); 
	}
	
	@Bean
	public DAOExceptionMapper daoExceptionMapper() {
		return new DAOExceptionMapper(); 
	}
	
	@Bean
	@DependsOn("springBus")
	public Server jaxRsServer(ApplicationContext appContext) {
		JAXRSServerFactoryBean factory = RuntimeDelegate
				.getInstance()
				.createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
		factory.setServiceBeans(Arrays.asList(restFacade()));
		factory.setAddress(factory.getAddress());
		factory.setProviders(Arrays.asList(jsonProvider(), daoExceptionMapper()));
		factory.setExtensionMappings(extensionMappings());
		factory.setOutFaultInterceptors(Arrays.asList(errorHandlerInterceptor()));
		factory.setBus(springBus());
		return factory.create();
	}
	
	private Map<Object, Object> extensionMappings() {
		Map<Object, Object> map = new HashMap<>();
		map.put("xml", MediaType.APPLICATION_ATOM_XML);
		map.put("json", MediaType.APPLICATION_JSON);
		return map;
	}
	
	@ApplicationPath(value = "")
	public class JaxRsApiApplication extends Application { }
 
	@Bean
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
 
	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}

}
