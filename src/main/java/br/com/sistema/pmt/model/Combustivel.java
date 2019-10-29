package br.com.sistema.pmt.model;

public enum Combustivel {
	
	GASOLINA("Gasolina"),
	ALCOOL("Álcool"),
	DIESEL("Diesel"),
	FLEX("Flex");
	
	private String descricao;
	
	Combustivel(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
