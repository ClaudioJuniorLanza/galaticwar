package br.com.starwarsresistence.galaticwar.exceptions;

public class QuantidadePontosInvalidoException extends Exception {
    public QuantidadePontosInvalidoException(){
        super(String.format("Ambos os lados deverão oferecer a mesma quantidade de pontos. Por exemplo, 1 arma\n" +
                "e 1 água (1 x 4 + 1 x 2) valem 6 comidas (6 x 1) ou 2 munições (2 x 3)"));
    }

}
