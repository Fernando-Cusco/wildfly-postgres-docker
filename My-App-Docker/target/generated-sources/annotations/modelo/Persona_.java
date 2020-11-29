package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Persona.class)
public abstract class Persona_ {

	public static volatile SingularAttribute<Persona, String> apellidos;
	public static volatile SingularAttribute<Persona, String> password;
	public static volatile SingularAttribute<Persona, String> cedula;
	public static volatile SingularAttribute<Persona, String> correo;
	public static volatile SingularAttribute<Persona, Integer> id;
	public static volatile SingularAttribute<Persona, Integer> edad;
	public static volatile SingularAttribute<Persona, String> nombres;

}

