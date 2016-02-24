package datos;

import datos.Grupo;
import datos.Persona;
import datos.PersonaTelefonoPK;
import datos.TelefonoTipo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(PersonaTelefono.class)
public class PersonaTelefono_ { 

    public static volatile ListAttribute<PersonaTelefono, Grupo> grupoList;
    public static volatile SingularAttribute<PersonaTelefono, Persona> persona1;
    public static volatile SingularAttribute<PersonaTelefono, TelefonoTipo> tipo;
    public static volatile SingularAttribute<PersonaTelefono, PersonaTelefonoPK> personaTelefonoPK;

}