package datos;

import datos.CpLocalidad;
import datos.Provincia;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Localidad.class)
public class Localidad_ { 

    public static volatile SingularAttribute<Localidad, Integer> id;
    public static volatile SingularAttribute<Localidad, String> nombre;
    public static volatile ListAttribute<Localidad, CpLocalidad> cpLocalidadList;
    public static volatile SingularAttribute<Localidad, Provincia> provincia;

}