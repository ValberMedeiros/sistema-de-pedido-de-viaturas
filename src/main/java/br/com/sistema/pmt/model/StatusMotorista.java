package br.com.sistema.pmt.model;

public enum StatusMotorista {
	
	PRONTO("Pronto"),
	MISSAO("Missao"),
	FERIAS("Ferias"),
	DISPENSADO("Dispensado");
	
	private String descricao;

	StatusMotorista(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}	
}
