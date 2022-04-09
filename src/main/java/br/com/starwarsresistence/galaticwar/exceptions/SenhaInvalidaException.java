package br.com.starwarsresistence.galaticwar.exceptions;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(){
        super("Senha inválida");
    }
}
