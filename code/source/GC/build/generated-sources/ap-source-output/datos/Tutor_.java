package datos;

import datos.Empresa;
import datos.Evento;
import datos.Grupo;
import datos.Persona;
import datos.TutoriaHorario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Tutor.class)
public class Tutor_ { 

    public static volatile ListAttribute<Tutor, Grupo> grupoList;
    public static volatile SingularAttribute<Tutor, String> nif;
    public static volatile SingularAttribute<Tutor, Empresa> empresa;
    public static volatile ListAttribute<Tutor, Evento> eventoList;
    public static volatile ListAttribute<Tutor, TutoriaHorario> tutoriaHorarioList;
    public static volatile SingularAttribute<Tutor, Persona> persona;

}