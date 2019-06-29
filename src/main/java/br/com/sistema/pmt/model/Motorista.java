package br.com.sistema.pmt.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Motorista implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Informe a identidade")
	private String identidade;
	
	@ManyToOne
    @org.hibernate.annotations.ForeignKey(name = "postoGraduacao_id")
    private PostoGraduacao postoGraduacaoMotorista;
	
	@NotBlank(message = "Informe o nome completo")
	private String nomeCompleto;
	
	@NotBlank(message = "Informe o nome de guerra")
	private String nomeDeGuerra;
	
	private StatusMotorista statusMotorista;
	
	@OneToMany(mappedBy = "motoristaDoPedido")
    private List<Pedido> pedidos;
		
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

	public PostoGraduacao getPostoGraduacaoMotorista() {
		return postoGraduacaoMotorista;
	}

	public void setPostoGraduacaoMotorista(PostoGraduacao postoGraduacao) {
		this.postoGraduacaoMotorista = postoGraduacao;
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

	public StatusMotorista getStatusMotorista() {
		return statusMotorista;
	}

	public void setStatusMotorista(StatusMotorista statusMotorista) {
		this.statusMotorista = statusMotorista;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Motorista other = (Motorista) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
