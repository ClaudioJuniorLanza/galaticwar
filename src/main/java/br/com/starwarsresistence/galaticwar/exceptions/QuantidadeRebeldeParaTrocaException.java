package br.com.starwarsresistence.galaticwar.exceptions;

public class QuantidadeRebeldeParaTrocaException extends Exception {
    public QuantidadeRebeldeParaTrocaException(){
        super(String.format("É necessário ter 2 rebeldes para realizar a troca de itens!"));
    }
}
