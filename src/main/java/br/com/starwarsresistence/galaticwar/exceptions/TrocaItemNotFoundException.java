package br.com.starwarsresistence.galaticwar.exceptions;

public class TrocaItemNotFoundException extends Exception{
    public TrocaItemNotFoundException(Long id){
        super(String.format("Negociação with ID %s not found!", id));
    }
}
