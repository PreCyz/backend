package backend.facade;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import backend.dto.LoggedUser;
import backend.dto.LoginDetails;

@Path("/rest")
public interface RestFacade extends ServiceFacade {
	
	@Path("/login")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	LoggedUser login(LoginDetails loginDetails);
	
	@Path("/login/{login}/{pass}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	LoggedUser login(@PathParam("login")String login, @PathParam("pass")String pass);
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	String test();
	
	/*@Path("/login/{login}/hashedPassword")
	@Produces(MediaType.APPLICATION_JSON)
	LoggedUser login(@PathParam("loginDetails")String login, @PathParam("hashedPassword")String hashedPassword);
*/
}
