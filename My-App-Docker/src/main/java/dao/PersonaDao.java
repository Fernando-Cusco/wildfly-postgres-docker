package dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Persona;

public class PersonaDao {

	@Inject
	private EntityManager em;
	
	public void add(Persona persona) {
		em.persist(persona);
	}
	
	public Persona search(String cedula) {
		String jpql = "select p from Persona p where p.cedula = :cedula";
		Query query = em.createQuery(jpql, Persona.class);
		query.setParameter("cedula", cedula);
		return (Persona) query.getSingleResult();
	}
	
	public List<Persona> getAll() {
		String jpql = "select p from Persona p";
		Query query = em.createQuery(jpql, Persona.class);
		List<Persona> personas = query.getResultList();
		return personas;
	}
	
}
