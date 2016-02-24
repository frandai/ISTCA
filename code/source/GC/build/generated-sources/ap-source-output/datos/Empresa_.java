package datos;

import datos.Actividad;
import datos.Comercial;
import datos.Direccion;
import datos.EmpresaEstado;
import datos.EmpresaMatricula;
import datos.EmpresaTelefono;
import datos.Evento;
import datos.Persona;
import datos.Proveedor;
import datos.Tutor;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Empresa.class)
public class Empresa_ { 

    public static volatile ListAttribute<Empresa, Tutor> tutorList;
    public static volatile SingularAttribute<Empresa, Direccion> direccion;
    public static volatile SingularAttribute<Empresa, EmpresaMatricula> empresaMatricula;
    public static volatile SingularAttribute<Empresa, String> nif;
    public static volatile SingularAttribute<Empresa, String> razonSocial;
    public static volatile SingularAttribute<Empresa, Actividad> actividad;
    public static volatile ListAttribute<Empresa, Evento> eventoList;
    public static volatile SingularAttribute<Empresa, String> pagWeb;
    public static volatile SingularAttribute<Empresa, EmpresaEstado> estado;
    public static volatile SingularAttribute<Empresa, String> email;
    public static volatile SingularAttribute<Empresa, Comercial> comercial;
    public static volatile SingularAttribute<Empresa, Persona> represLegal;
    public static volatile ListAttribute<Empresa, Persona> personaList;
    public static volatile SingularAttribute<Empresa, Proveedor> proveedor;
    public static volatile ListAttribute<Empresa, EmpresaTelefono> empresaTelefonoList;

}