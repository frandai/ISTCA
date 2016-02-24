package datos;

import datos.Comercial;
import datos.Matricula;
import datos.MatriculaComercialPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(MatriculaComercial.class)
public class MatriculaComercial_ { 

    public static volatile SingularAttribute<MatriculaComercial, Double> porcentaje;
    public static volatile SingularAttribute<MatriculaComercial, Matricula> matricula1;
    public static volatile SingularAttribute<MatriculaComercial, MatriculaComercialPK> matriculaComercialPK;
    public static volatile SingularAttribute<MatriculaComercial, Comercial> comercial1;

}