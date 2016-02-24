package datos;

import datos.ActividadGrupo;
import datos.Empresa;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Actividad.class)
public class Actividad_ { 

    public static volatile SingularAttribute<Actividad, Integer> id;
    public static volatile SingularAttribute<Actividad, String> nombre;
    public static volatile ListAttribute<Actividad, Empresa> empresaList;
    public static volatile SingularAttribute<Actividad, ActividadGrupo> actividadGrupo;

}