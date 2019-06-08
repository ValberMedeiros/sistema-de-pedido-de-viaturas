package br.com.sistema.pmt.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class PostoGraduacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postoGraduacao_id;

    @NotBlank
    private String postoGrad;

    @OneToMany(mappedBy = "postoGraduacao")
    private List<Usuarios> usuarios;

    @OneToMany(mappedBy = "postoGraduacaoMotorista")
    private List<Motorista> motoristas;

    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public Long getPostoGraduacao_id() {
        return postoGraduacao_id;
    }

    public void setPostoGraduacao_id(Long postoGraduacao_id) {
        this.postoGraduacao_id = postoGraduacao_id;
    }

    public String getPostoGrad() {
        return postoGrad;
    }

    public void setPostoGrad(String postoGrad) {
        this.postoGrad = postoGrad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostoGraduacao that = (PostoGraduacao) o;
        return Objects.equals(getPostoGraduacao_id(), that.getPostoGraduacao_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostoGraduacao_id());
    }
}
