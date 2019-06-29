package br.com.sistema.pmt.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@org.hibernate.annotations.ForeignKey(name = "usuario_id")
	private Usuarios usuarioRemetente;
	
	@ManyToOne
	@org.hibernate.annotations.ForeignKey(name = "motorista_id")
	private Motorista motoristaDoPedido;
	
	@ManyToOne
	@org.hibernate.annotations.ForeignKey(name = "viatura_id")
	private Viatura viaturaDoPedido;
	
	@NotBlank(message = "Informe o local de embarque")
	private String localDeEmbarque;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Informe a data de embarque")
	private LocalDate dataDeEmbarque;
	
	@NotNull(message = "Informe a hora de embarque")
	private LocalTime horaDoEmbarque;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataDoPedido;
	
	@NotBlank(message = "Informe o tipo da viatura")
	private String tipoDeViatura;
	
	@NotBlank(message = "Informe a finalidade do pedido")
	private String finalidadeDoPedido;
	
	@NotBlank(message = "Informe o destino")
	private String destino;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataDeEmbarque() {
		return dataDeEmbarque;
	}

	public void setDataDeEmbarque(LocalDate dataDeEmbarque) {
		this.dataDeEmbarque = dataDeEmbarque;
	}

	public Usuarios getUsuarioRemetente() {
		return usuarioRemetente;
	}

	public void setUsuarioRemetente(Usuarios usuarioRemetente) {
		this.usuarioRemetente = usuarioRemetente;
	}

	public Motorista getMotoristaDoPedido() {
		return motoristaDoPedido;
	}

	public void setMotoristaDoPedido(Motorista motoristaDoPedido) {
		this.motoristaDoPedido = motoristaDoPedido;
	}

	public Viatura getViaturaDoPedido() {
		return viaturaDoPedido;
	}

	public void setViaturaDoPedido(Viatura viaturaDoPedido) {
		this.viaturaDoPedido = viaturaDoPedido;
	}

	public String getLocalDeEmbarque() {
		return localDeEmbarque;
	}

	public void setLocalDeEmbarque(String localDeEmbarque) {
		this.localDeEmbarque = localDeEmbarque;
	}

	public LocalTime getHoraDoEmbarque() {
		return horaDoEmbarque;
	}

	public void setHoraDoEmbarque(LocalTime horaDoEmbarque) {
		this.horaDoEmbarque = horaDoEmbarque;
	}

	public LocalDate getDataDoPedido() {
		return dataDoPedido;
	}

	public void setDataDoPedido(LocalDate dataDoPedido) {
		this.dataDoPedido = dataDoPedido;
	}

	public String getTipoDeViatura() {
		return tipoDeViatura;
	}

	public void setTipoDeViatura(String tipoDeViatura) {
		this.tipoDeViatura = tipoDeViatura;
	}

	public String getFinalidadeDoPedido() {
		return finalidadeDoPedido;
	}

	public void setFinalidadeDoPedido(String finalidadeDoPedido) {
		this.finalidadeDoPedido = finalidadeDoPedido;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
