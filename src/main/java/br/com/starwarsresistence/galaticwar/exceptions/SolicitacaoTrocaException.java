package br.com.starwarsresistence.galaticwar.exceptions;

public class SolicitacaoTrocaException extends RuntimeException {
    public SolicitacaoTrocaException(){
        super(String.format("Falha ao solicitar a troca de itens"));
    }
}
