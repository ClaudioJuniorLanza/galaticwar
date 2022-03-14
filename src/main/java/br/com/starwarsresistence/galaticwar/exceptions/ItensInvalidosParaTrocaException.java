package br.com.starwarsresistence.galaticwar.exceptions;

public class ItensInvalidosParaTrocaException extends Exception {
    public ItensInvalidosParaTrocaException(String perfilSolicitacao) {
        super(String.format("Itens inválidos na lista. Verifique o inventário do %s e reenvie a solicitação de troca ", perfilSolicitacao));
    }
}
