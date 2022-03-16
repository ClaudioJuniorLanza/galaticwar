package br.com.starwarsresistence.galaticwar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuantidadeRebeldeParaTrocaException extends Exception {
    public QuantidadeRebeldeParaTrocaException(){
        super(String.format("É necessário ter 2 rebeldes para realizar a troca de itens!"));
    }
}
