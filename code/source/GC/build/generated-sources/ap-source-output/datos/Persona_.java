package datos;

import datos.Alumno;
import datos.Comercial;
import datos.Direccion;
import datos.Empresa;
import datos.Evento;
import datos.PersonaTelefono;
import datos.Tutor;
import datos.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile SingularAttribute<Persona, String> apellido2;
    public static volatile SingularAttribute<Persona, Direccion> direccion;
    public static volatile SingularAttribute<Persona, String> nif;
    public static volatile SingularAttribute<Persona, Usuario> usuario;
    public static volatile SingularAttribute<Persona, String> apellido1;
    public static volatile ListAttribute<Persona, Evento> eventoList;
    public static volatile ListAttribute<Persona, Empresa> empresaList1;
    public static volatile SingularAttribute<Persona, Alumno> alumno;
    public static volatile SingularAttribute<Persona, Date> fechaNacimiento;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile ListAttribute<Persona, Empresa> empresaList;
    public static volatile SingularAttribute<Persona, String> email;
    public static volatile SingularAttribute<Persona, Comercial> comercial;
    public static volatile SingularAttribute<Persona, String> nss;
    public static volatile SingularAttribute<Persona, Tutor> tutor;
    public static volatile ListAttribute<Persona, PersonaTelefono> personaTelefonoList;
    public static volatile SingularAttribute<Persona, Boolean> masculino;

}