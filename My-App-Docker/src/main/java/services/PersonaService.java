package services;

import java.util.List;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import modelo.Persona;
import negocio.PersonaOn;

@Path("/persona")
public class PersonaService {

	@Inject
	private PersonaOn on;
	
	@POST
	@Path("/add")
	@Consumes({MediaType.APPLICATION_JSON})
	public void add(Persona persona) {
		System.out.println("Add persona: "+ persona.toString());
		on.add(persona);
	}
	
	@GET
	@Path("/search")
	@Produces({MediaType.APPLICATION_JSON})
	public Persona search(@QueryParam("cedula") String cedula) {
		System.out.println("Search persona: "+ cedula);
		return on.search(cedula);
	}
	
	@GET
	@Path("getAll")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Persona> getAll() {
		System.out.println("getAll persona");
		return on.getAll();
	}
	
}
