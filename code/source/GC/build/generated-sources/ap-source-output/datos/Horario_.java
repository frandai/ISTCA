package datos;

import datos.TutoriaHorario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Horario.class)
public class Horario_ { 

    public static volatile SingularAttribute<Horario, Integer> id;
    public static volatile SingularAttribute<Horario, Date> horaInicio;
    public static volatile SingularAttribute<Horario, Date> horaFin;
    public static volatile ListAttribute<Horario, TutoriaHorario> tutoriaHorarioList;

}