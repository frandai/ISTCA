package datos;

import datos.Grupo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(DiaSemana.class)
public class DiaSemana_ { 

    public static volatile SingularAttribute<DiaSemana, String> id;
    public static volatile SingularAttribute<DiaSemana, String> nombre;
    public static volatile SingularAttribute<DiaSemana, Short> orden;
    public static volatile ListAttribute<DiaSemana, Grupo> grupoList;

}