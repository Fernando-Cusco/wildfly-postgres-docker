package negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dao.PersonaDao;
import modelo.Persona;

@Stateless
public class PersonaOn {
	
	@Inject
	private PersonaDao dao;
	
	public void add(Persona persona) {
		dao.add(persona);
	}
	
	public Persona search(String cedula) {
		return dao.search(cedula);
	}
	
	public List<Persona> getAll() {
		return dao.getAll();
	}

}
