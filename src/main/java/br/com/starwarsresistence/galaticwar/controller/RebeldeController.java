package br.com.starwarsresistence.galaticwar.controller;

import br.com.starwarsresistence.galaticwar.dto.request.LocalizacaoDTO;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import br.com.starwarsresistence.galaticwar.exceptions.*;
import br.com.starwarsresistence.galaticwar.service.RebeldeService;
import br.com.starwarsresistence.galaticwar.service.TrocaItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/rebelde")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RebeldeController {

    private final RebeldeService rebeldeService;
    private final TrocaItemService trocaItemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RebeldeDTO> listAll(){
        return rebeldeService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<RebeldeDTO> findRebeldeById(@PathVariable Long id) throws RebeldeNotFoundException {
        return rebeldeService.findById(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RebeldeDTO create(@RequestBody RebeldeDTO rebeldeDTO){
        return rebeldeService.save(rebeldeDTO);
    }

    @PatchMapping("/related-traidor/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void relatedTraidor(@PathVariable Long id) throws RebeldeNotFoundException {
        rebeldeService.relatedTraidor(id);
    }

    @PatchMapping("/update-localizacao/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocalizacao(@PathVariable Long id, @RequestBody LocalizacaoDTO localizacaoDTO) throws RebeldeNotFoundException {
        rebeldeService.updateLocalizacao(id, localizacaoDTO);
    }

    @PostMapping("/negociar-itens")
    @ResponseStatus(HttpStatus.CREATED)
    public Long solicitaTrocaItem(@RequestBody TrocaItemDTO trocaItem) throws TraidorException,
            QuantidadeRebeldeParaTrocaException, ItensInvalidosParaTrocaException,
            QuantidadePontosInvalidoException, SolicitacaoTrocaException {
        return trocaItemService.solicitaTroca(trocaItem);
    }

    @GetMapping("/confirm-negociacao/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmaTroca(@PathVariable Long id) throws StatusTrocaException, TrocaItemNotFoundException, RebeldeNotFoundException, SolicitacaoTrocaException {
        trocaItemService.efetuaTroca(id);
    }

    @GetMapping("/rejeita-negociacao/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejeitaTroca(@PathVariable Long id) throws TrocaItemNotFoundException, SolicitacaoTrocaException {
        trocaItemService.rejeitaTroca(id);
    }

    @GetMapping("/solicitacoes-troca-enviada/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TrocaItemDTO> searchSolicitacaoTrocaEnviada(@PathVariable Long id) {
        return trocaItemService.searchSolicitacaoTrocaEnviada(id);
    }

    @GetMapping("/solicitacoes-troca-recebida/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TrocaItemDTO> searchSolicitacaoTrocaRecebida(@PathVariable Long id) {
        return trocaItemService.searchSolicitacaoRecebida(id);
    }

}
