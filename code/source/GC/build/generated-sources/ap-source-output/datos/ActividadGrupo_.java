package datos;

import datos.Actividad;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(ActividadGrupo.class)
public class ActividadGrupo_ { 

    public static volatile SingularAttribute<ActividadGrupo, Integer> id;
    public static volatile SingularAttribute<ActividadGrupo, String> nombre;
    public static volatile ListAttribute<ActividadGrupo, Actividad> actividadList;

}