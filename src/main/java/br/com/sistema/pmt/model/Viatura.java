package br.com.sistema.pmt.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity	
public class Viatura implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotBlank
    private String placa;
	
	@NotNull
    private Long renavan;
	
	@NotBlank
    private String EB;
	
	@NotBlank
    private String NEE;

	@NotBlank
    private String chassi;

	
    private Combustivel combustivel;

	@NotNull
    private Long capacidadePassageiros;

	@NotNull
    private Long quantidadeDePortas;

	@NotNull
    private Long capacidadeDoTanque;

	@NotNull
    private Long autonomia;

	@NotBlank
    private String utilização;
	
	
	private StatusViatura statusViatura;
 
    private static final long serialVersionUID = 1L;

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
		Viatura other = (Viatura) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Long getRenavan() {
		return renavan;
	}

	public void setRenavan(Long renavan) {
		this.renavan = renavan;
	}

	public String getEB() {
		return EB;
	}

	public void setEB(String eB) {
		EB = eB;
	}

	public String getNEE() {
		return NEE;
	}

	public void setNEE(String nEE) {
		NEE = nEE;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public Long getCapacidadePassageiros() {
		return capacidadePassageiros;
	}

	public void setCapacidadePassageiros(Long capacidadePassageiros) {
		this.capacidadePassageiros = capacidadePassageiros;
	}

	public Long getQuantidadeDePortas() {
		return quantidadeDePortas;
	}

	public void setQuantidadeDePortas(Long quantidadeDePortas) {
		this.quantidadeDePortas = quantidadeDePortas;
	}
	
	public Combustivel getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(Combustivel combustivel) {
		this.combustivel = combustivel;
	}

	public Long getCapacidadeDoTanque() {
		return capacidadeDoTanque;
	}

	public void setCapacidadeDoTanque(Long capacidadeDoTanque) {
		this.capacidadeDoTanque = capacidadeDoTanque;
	}

	public Long getAutonomia() {
		return autonomia;
	}

	public void setAutonomia(Long autonomia) {
		this.autonomia = autonomia;
	}

	public String getUtilização() {
		return utilização;
	}

	public void setUtilização(String utilização) {
		this.utilização = utilização;
	}

	public StatusViatura getStatusViatura() {
		return statusViatura;
	}

	public void setStatusViatura(StatusViatura statusViatura) {
		this.statusViatura = statusViatura;
	}

}
