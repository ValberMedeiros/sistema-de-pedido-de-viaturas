package br.com.sistema.pmt.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Motorista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Informe a identidade")
    private String identidade;

    @NotBlank(message = "Informe o nome completo")
    private String nomeCompleto;

    @NotBlank(message = "Informe o nome de guerra")
    private String nomeDeGuerra;

    @ManyToOne
    @org.hibernate.annotations.ForeignKey(name = "postoGraduacao_id")
    private PostoGraduacao postoGraduacaoMotorista;
    
    private StatusMotorista statusMotorista;

    public StatusMotorista getStatusMotorista() {
		return statusMotorista;
	}

	public void setStatusMotorista(StatusMotorista statusMotorista) {
		this.statusMotorista = statusMotorista;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeDeGuerra() {
        return nomeDeGuerra;
    }

    public void setNomeDeGuerra(String nomeDeGuerra) {
        this.nomeDeGuerra = nomeDeGuerra;
    }

    public PostoGraduacao getPostoGraduacaoMotorista() {
        return postoGraduacaoMotorista;
    }

    public void setPostoGraduacaoMotorista(PostoGraduacao postoGraduacaoMotorista) {
        this.postoGraduacaoMotorista = postoGraduacaoMotorista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Motorista motorista = (Motorista) o;
        return Objects.equals(getId(), motorista.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    private static final long serialVersionUID = 1L;
}
