package datos;

import datos.AccionFormativa;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(AccionFormativaCncp.class)
public class AccionFormativaCncp_ { 

    public static volatile SingularAttribute<AccionFormativaCncp, Integer> id;
    public static volatile SingularAttribute<AccionFormativaCncp, String> realDecreto;
    public static volatile SingularAttribute<AccionFormativaCncp, String> unidadCompetencia;
    public static volatile SingularAttribute<AccionFormativaCncp, String> certificado;
    public static volatile ListAttribute<AccionFormativaCncp, AccionFormativa> accionFormativaList;

}