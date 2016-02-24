package datosVista;

import datos.AccionFormativa;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Accionformativavista.class)
public class Accionformativavista_ { 

    public static volatile SingularAttribute<Accionformativavista, Integer> id;
    public static volatile SingularAttribute<Accionformativavista, String> nombre;
    public static volatile SingularAttribute<Accionformativavista, String> modalidad;
    public static volatile SingularAttribute<Accionformativavista, Double> precio;
    public static volatile SingularAttribute<Accionformativavista, Short> horas;
    public static volatile SingularAttribute<Accionformativavista, String> familia;
    public static volatile SingularAttribute<Accionformativavista, String> proveedor;
    public static volatile SingularAttribute<Accionformativavista, AccionFormativa> accionFormativa;

}