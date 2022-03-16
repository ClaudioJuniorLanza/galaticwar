package br.com.starwarsresistence.galaticwar.controller;

import br.com.starwarsresistence.galaticwar.dto.response.MessageResponse;
import br.com.starwarsresistence.galaticwar.service.RelatorioRebeldeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/relatorio-rebelde", produces = "application/json")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Relatório Rebeldes", description = "Obtem dados estatísticos sobre os rebeldes")
public class RelatorioRebeldeController {

    private final RelatorioRebeldeService relatorioRebeldeService;

    @Operation(description = "Retorna a porcentagem de Traidores")
    @GetMapping("/porcentagem-traidores")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse getPorcentagemTraidores(){
        String tipoFiltro = "TRAIDOR";
        return relatorioRebeldeService.getPorcentagemRebedesOrTraidores(tipoFiltro);
    }

    @Operation(description = "Retorna a porcentagem de Rebeldes válidos")
    @GetMapping("/porcentagem-rebeldes")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse getPorcentagemRebeldes(){
        String tipoFiltro = "REBELDE";
        return relatorioRebeldeService.getPorcentagemRebedesOrTraidores(tipoFiltro);
    }

    @Operation(description = "Obtem a media dos recursos por rebelde")
    @GetMapping("/media-recursos")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponse> getMediaRecursos(){
        return relatorioRebeldeService.getMediaRecursos();
    }
}
