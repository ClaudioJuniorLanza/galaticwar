package br.com.starwarsresistence.galaticwar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItensInvalidosParaTrocaException extends Exception {
    public ItensInvalidosParaTrocaException(String perfilSolicitacao) {
        super(String.format("Itens inválidos na lista. Verifique o inventário do %s e reenvie a solicitação de troca ", perfilSolicitacao));
    }
}
