package br.com.starwarsresistence.galaticwar.enums;

public enum ItensInventario {
    ARMA("Arma"),
    MUNICAO("Munição"),
    AGUA("Água"),
    COMIDA("Comida");

    private String descricao;

    ItensInventario(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
