package br.com.starwarsresistence.galaticwar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrocaItemNotFoundException extends Exception{
    public TrocaItemNotFoundException(Long id){
        super(String.format("Negociação with ID %s not found!", id));
    }
}
