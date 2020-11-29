package controllador;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import modelo.Persona;
import negocio.PersonaOn;

@ManagedBean
@ViewScoped
public class PersonaController {
	
	@Inject
	private PersonaOn on;
	
	private Persona persona;
	
	private List<Persona> personas;
	
	private String cedula;
	
	@PostConstruct
	public void init() {
		persona = new Persona();
		personas = on.getAll();
	}
	
	public String search() {
		persona = on.search(cedula);
		return "";
	}
	
	public String add() {
		on.add(persona);
		persona =  null;
		return "";
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	
}
