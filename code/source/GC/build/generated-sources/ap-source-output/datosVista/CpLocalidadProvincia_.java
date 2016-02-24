package datosVista;

import datos.CpLocalidad;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(CpLocalidadProvincia.class)
public class CpLocalidadProvincia_ { 

    public static volatile SingularAttribute<CpLocalidadProvincia, Integer> cp;
    public static volatile SingularAttribute<CpLocalidadProvincia, CpLocalidad> cpLoc;
    public static volatile SingularAttribute<CpLocalidadProvincia, Integer> provinciaId;
    public static volatile SingularAttribute<CpLocalidadProvincia, String> provinciaNombre;
    public static volatile SingularAttribute<CpLocalidadProvincia, Integer> localidadId;
    public static volatile SingularAttribute<CpLocalidadProvincia, String> localidadNombre;

}