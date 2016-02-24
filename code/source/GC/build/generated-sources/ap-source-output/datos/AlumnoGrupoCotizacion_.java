package datos;

import datos.Alumno;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(AlumnoGrupoCotizacion.class)
public class AlumnoGrupoCotizacion_ { 

    public static volatile SingularAttribute<AlumnoGrupoCotizacion, Short> id;
    public static volatile SingularAttribute<AlumnoGrupoCotizacion, String> nombre;
    public static volatile ListAttribute<AlumnoGrupoCotizacion, Alumno> alumnoList;

}