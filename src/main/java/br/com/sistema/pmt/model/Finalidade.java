package br.com.sistema.pmt.model;

public enum Finalidade {

    MATERIAL("Transporte de Material"),
    PESSOAL("Transporte de Pessoal");

    private String descricao;

    Finalidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
