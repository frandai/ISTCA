package datos;

import datos.CpLocalidadPK;
import datos.Direccion;
import datos.Localidad;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(CpLocalidad.class)
public class CpLocalidad_ { 

    public static volatile SingularAttribute<CpLocalidad, CpLocalidadPK> cpLocalidadPK;
    public static volatile ListAttribute<CpLocalidad, Direccion> direccionList;
    public static volatile SingularAttribute<CpLocalidad, Localidad> localidad1;

}