package datos;

import datos.Grupo;
import datos.Horario;
import datos.Tutor;
import datos.TutoriaHorarioPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(TutoriaHorario.class)
public class TutoriaHorario_ { 

    public static volatile SingularAttribute<TutoriaHorario, Grupo> grupo1;
    public static volatile SingularAttribute<TutoriaHorario, Short> horas;
    public static volatile SingularAttribute<TutoriaHorario, Horario> horario1;
    public static volatile SingularAttribute<TutoriaHorario, Tutor> tutor1;
    public static volatile SingularAttribute<TutoriaHorario, TutoriaHorarioPK> tutoriaHorarioPK;

}