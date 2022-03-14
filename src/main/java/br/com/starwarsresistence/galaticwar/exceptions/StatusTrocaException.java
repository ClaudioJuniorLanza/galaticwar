package br.com.starwarsresistence.galaticwar.exceptions;

public class StatusTrocaException extends Throwable {
    public StatusTrocaException(String descricao) {
        super(String.format("O status atual da solicitação de troca está como %s . Não é possível a troca ser efetuada", descricao));
    }
}
