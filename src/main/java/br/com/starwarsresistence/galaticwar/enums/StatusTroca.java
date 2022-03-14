package br.com.starwarsresistence.galaticwar.enums;

public enum StatusTroca {

    PENDENTE("Pendente"),
    ACEITO("Aceito"),
    RECUSADO("Recusado");

    private String descricao;

    StatusTroca(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
