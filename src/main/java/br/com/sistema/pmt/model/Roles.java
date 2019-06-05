package br.com.sistema.pmt.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roles_id;

    @NotBlank
    private String tipo;

    @OneToMany(mappedBy = "roles")
    private List<Usuarios> usuarios;

    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getRoles_id() {
        return roles_id;
    }

    public void setRoles_id(Long roles_id) {
        this.roles_id = roles_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return Objects.equals(getRoles_id(), roles.getRoles_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoles_id());
    }
}
