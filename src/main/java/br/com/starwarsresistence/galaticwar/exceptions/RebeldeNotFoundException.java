package br.com.starwarsresistence.galaticwar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RebeldeNotFoundException extends Exception {
    public RebeldeNotFoundException(Long id) {
        super(String.format("Rebelde with ID %s not found!", id));
    }
}
