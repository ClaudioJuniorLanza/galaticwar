package br.com.starwarsresistence.galaticwar.exceptions;

public class RebeldeNotFoundException extends Exception {
    public RebeldeNotFoundException(Long id) {
        super(String.format("Rebelde with ID %s not found!", id));
    }
}
