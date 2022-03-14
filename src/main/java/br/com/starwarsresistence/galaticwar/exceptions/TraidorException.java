package br.com.starwarsresistence.galaticwar.exceptions;

public class TraidorException extends Exception{
    public TraidorException(){
        super(String.format("Não foi possível realizar troca. Existe rebelde traidor na negociação!"));
    }
}
