package datos;

import datos.Comercial;
import datos.ComercialTipo;
import datos.Empresa;
import datos.MatriculaComercial;
import datos.Persona;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Comercial.class)
public class Comercial_ { 

    public static volatile SingularAttribute<Comercial, Integer> id;
    public static volatile ListAttribute<Comercial, Empresa> empresaList;
    public static volatile ListAttribute<Comercial, MatriculaComercial> matriculaComercialList;
    public static volatile ListAttribute<Comercial, Persona> personaList;
    public static volatile SingularAttribute<Comercial, Comercial> comercialSuperior;
    public static volatile ListAttribute<Comercial, Comercial> comercialList;
    public static volatile SingularAttribute<Comercial, ComercialTipo> comercialTipo;

}