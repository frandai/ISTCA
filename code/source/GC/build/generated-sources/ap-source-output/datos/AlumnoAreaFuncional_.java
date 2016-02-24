package datos;

import datos.Alumno;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(AlumnoAreaFuncional.class)
public class AlumnoAreaFuncional_ { 

    public static volatile SingularAttribute<AlumnoAreaFuncional, Short> id;
    public static volatile SingularAttribute<AlumnoAreaFuncional, String> nombre;
    public static volatile ListAttribute<AlumnoAreaFuncional, Alumno> alumnoList;

}