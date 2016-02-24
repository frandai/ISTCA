package datos;

import datos.Empresa;
import datos.EventoTipo;
import datos.Matricula;
import datos.Persona;
import datos.Tutor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-07-07T19:23:36")
@StaticMetamodel(Evento.class)
public class Evento_ { 

    public static volatile SingularAttribute<Evento, Integer> id;
    public static volatile SingularAttribute<Evento, Date> fechaVencimiento;
    public static volatile SingularAttribute<Evento, Tutor> tutor;
    public static volatile SingularAttribute<Evento, EventoTipo> tipo;
    public static volatile SingularAttribute<Evento, String> descripcion;
    public static volatile SingularAttribute<Evento, Empresa> empresa;
    public static volatile SingularAttribute<Evento, Persona> creador;
    public static volatile SingularAttribute<Evento, Date> fechaRealizacion;
    public static volatile SingularAttribute<Evento, Date> fechaCreacion;
    public static volatile SingularAttribute<Evento, String> observaciones;
    public static volatile SingularAttribute<Evento, Matricula> matricula;

}