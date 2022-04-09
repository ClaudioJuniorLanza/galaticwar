package br.com.starwarsresistence.galaticwar.controller;

import br.com.starwarsresistence.galaticwar.dto.request.LocalizacaoDTO;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import br.com.starwarsresistence.galaticwar.exceptions.*;
import br.com.starwarsresistence.galaticwar.service.RebeldeService;
import br.com.starwarsresistence.galaticwar.service.TrocaItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/rebelde", produces = "application/json")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Rebelde", description = "Cadastro e troca de recursos entre rebeldes")
public class RebeldeController {

    private final RebeldeService rebeldeService;
    private final TrocaItemService trocaItemService;

    @Operation(description = "Retorna uma lista de todos os rebeldes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('REBELDE')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RebeldeDTO> listAll(@PageableDefault(size = 5)
                                        @SortDefault.SortDefaults({
                                                @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                                                @SortDefault(sort = "id", direction = Sort.Direction.ASC)})
                                            Pageable pageable){
        return rebeldeService.findAll(pageable);
    }

    @Operation(description = "Retorna um Rebelde a partir do seu ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<RebeldeDTO> findRebeldeById(@PathVariable Long id) throws RebeldeNotFoundException {
        return rebeldeService.findById(Long.valueOf(id));
    }

    @Operation(description = "Realiza a criação de um rebelde")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RebeldeDTO create(@RequestBody RebeldeDTO rebeldeDTO){
        return rebeldeService.save(rebeldeDTO);
    }

    @Operation(description = "Informa um rebelde como traidor informando seu código")
    @PreAuthorize("hasRole('REBELDE')")
    @PatchMapping("/related-traidor/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void relatedTraidor(@PathVariable Long idRebeldeTraidor) throws RebeldeNotFoundException {
        rebeldeService.relatedTraidor(idRebeldeTraidor);
    }

    @Operation(description = "Atualiza a localização do Rebelde")
    @PreAuthorize("hasRole('REBELDE')")
    @PatchMapping("/update-localizacao/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLocalizacao(@PathVariable Long id, @RequestBody LocalizacaoDTO localizacaoDTO) throws RebeldeNotFoundException {
        rebeldeService.updateLocalizacao(id, localizacaoDTO);
    }

    @Operation(description = "Realiza uma solicitação de troca de itens do inventário entre 2 rebeldes")
    @PreAuthorize("hasRole('REBELDE')")
    @PostMapping("/negociar-itens")
    @ResponseStatus(HttpStatus.CREATED)
    public Long solicitaTrocaItem(@RequestBody TrocaItemDTO trocaItem) throws TraidorException,
            QuantidadeRebeldeParaTrocaException, ItensInvalidosParaTrocaException,
            QuantidadePontosInvalidoException, SolicitacaoTrocaException {
        return trocaItemService.solicitaTroca(trocaItem);
    }

    @Operation(description = "Confirma solicitação troca de itens entre rebeldes")
    @PreAuthorize("hasRole('REBELDE')")
    @GetMapping("/confirm-negociacao/{idSolicitacao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmaTroca(@PathVariable Long idSolicitacao) throws StatusTrocaException, TrocaItemNotFoundException, RebeldeNotFoundException, SolicitacaoTrocaException {
        trocaItemService.efetuaTroca(idSolicitacao);
    }

    @Operation(description = "Rejeita solicitação troca de itens entre rebeldes")
    @PreAuthorize("hasRole('REBELDE')")
    @GetMapping("/rejeita-negociacao/{idSolicitacao}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejeitaTroca(@PathVariable Long idSolicitacao) throws TrocaItemNotFoundException, SolicitacaoTrocaException {
        trocaItemService.rejeitaTroca(idSolicitacao);
    }

    @Operation(description = "Retorna uma lista de solicitações enviadas para troca de itens do inventário")
    @PreAuthorize("hasRole('REBELDE')")
    @GetMapping("/solicitacoes-troca-enviada/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TrocaItemDTO> searchSolicitacaoTrocaEnviada(@PathVariable Long id) {
        return trocaItemService.searchSolicitacaoTrocaEnviada(id);
    }

    @Operation(description = "Retorna uma lista de solicitações recebidas para troca de itens do inventário")
    @PreAuthorize("hasRole('REBELDE')")
    @GetMapping("/solicitacoes-troca-recebida/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TrocaItemDTO> searchSolicitacaoTrocaRecebida(@PathVariable Long id) {
        return trocaItemService.searchSolicitacaoRecebida(id);
    }

}
