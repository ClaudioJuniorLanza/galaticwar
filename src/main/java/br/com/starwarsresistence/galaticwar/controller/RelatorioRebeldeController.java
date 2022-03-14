package br.com.starwarsresistence.galaticwar.controller;

import br.com.starwarsresistence.galaticwar.dto.response.MessageResponse;
import br.com.starwarsresistence.galaticwar.service.RelatorioRebeldeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/relatorio-rebelde")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RelatorioRebeldeController {

    private final RelatorioRebeldeService relatorioRebeldeService;

    @GetMapping("/porcentagem-traidores")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse getPorcentagemTraidores(){
        return relatorioRebeldeService.getPorcentagemTraidores();
    }

    @GetMapping("/porcentagem-rebeldes")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse getPorcentagemRebeldes(){
        return relatorioRebeldeService.getPorcentagemRebeldes();
    }

}
