package datos;

import datos.CpLocalidad;
import datos.Empresa;
import datos.Grupo;
import datos.Matricula;
import datos.Persona;
import datos.TipoVia;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Direccion.class)
public class Direccion_ { 

    public static volatile SingularAttribute<Direccion, Integer> id;
    public static volatile ListAttribute<Direccion, Empresa> empresaList;
    public static volatile ListAttribute<Direccion, Grupo> grupoList;
    public static volatile SingularAttribute<Direccion, String> via;
    public static volatile SingularAttribute<Direccion, String> extra;
    public static volatile ListAttribute<Direccion, Matricula> matriculaList;
    public static volatile SingularAttribute<Direccion, TipoVia> tipoVia;
    public static volatile SingularAttribute<Direccion, CpLocalidad> cpLocalidad;
    public static volatile ListAttribute<Direccion, Persona> personaList;
    public static volatile SingularAttribute<Direccion, String> numero;

}