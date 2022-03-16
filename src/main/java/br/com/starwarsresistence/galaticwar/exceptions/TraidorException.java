package br.com.starwarsresistence.galaticwar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TraidorException extends Exception{
    public TraidorException(){
        super(String.format("Não foi possível realizar troca. Existe rebelde traidor na negociação!"));
    }
}
