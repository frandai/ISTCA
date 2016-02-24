/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 *
 */
@Embeddable
public class TutoriaHorarioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACCION_FORMATIVA", nullable = false)
    private int accionFormativa;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int grupo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String tutor;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int horario;

    public TutoriaHorarioPK() {
    }

    public TutoriaHorarioPK(int accionFormativa, int grupo, String tutor, int horario) {
        this.accionFormativa = accionFormativa;
        this.grupo = grupo;
        this.tutor = tutor;
        this.horario = horario;
    }

    public int getAccionFormativa() {
        return accionFormativa;
    }

    public void setAccionFormativa(int accionFormativa) {
        this.accionFormativa = accionFormativa;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) accionFormativa;
        hash += (int) grupo;
        hash += (tutor != null ? tutor.hashCode() : 0);
        hash += (int) horario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TutoriaHorarioPK)) {
            return false;
        }
        TutoriaHorarioPK other = (TutoriaHorarioPK) object;
        if (this.accionFormativa != other.accionFormativa) {
            return false;
        }
        if (this.grupo != other.grupo) {
            return false;
        }
        if ((this.tutor == null && other.tutor != null) || (this.tutor != null && !this.tutor.equals(other.tutor))) {
            return false;
        }
        if (this.horario != other.horario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.TutoriaHorarioPK[ accionFormativa=" + accionFormativa + ", grupo=" + grupo + ", tutor=" + tutor + ", horario=" + horario + " ]";
    }
    
}
