package br.com.starwarsresistence.galaticwar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class StatusTrocaException extends RuntimeException {
    public StatusTrocaException(String descricao) {
        super(String.format("O status atual da solicitação de troca está como %s . Não é possível a troca ser efetuada", descricao));
    }
}
