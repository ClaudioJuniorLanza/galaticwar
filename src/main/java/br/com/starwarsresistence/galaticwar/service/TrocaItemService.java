package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.domain.TrocaItem;
import br.com.starwarsresistence.galaticwar.dto.mapper.InventarioMapper;
import br.com.starwarsresistence.galaticwar.dto.mapper.TrocaItemMapper;
import br.com.starwarsresistence.galaticwar.dto.request.InventarioDTO;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import br.com.starwarsresistence.galaticwar.enums.StatusTroca;
import br.com.starwarsresistence.galaticwar.exceptions.*;
import br.com.starwarsresistence.galaticwar.repository.TrocaItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TrocaItemService {

    private static final Integer ARMA = 4;
    private static final Integer MUNICAO = 3;
    private static final Integer AGUA = 2;
    private static final Integer COMIDA = 1;

    private static final char SOMA = '+';
    private static final char SUBTRACAO = '-';
    private static final char MULTIPLICACAO = '*';
    private static final char DIVISAO = '/';

    private final RebeldeService rebeldeService;
    private final InventarioMapper inventarioMapper;
    private final TrocaItemMapper trocaItemMapper;
    private final TrocaItemRepository trocaItemRepository;

    public Long solicitaTroca(TrocaItemDTO trocaItemDTO) throws TraidorException, QuantidadeRebeldeParaTrocaException,
            ItensInvalidosParaTrocaException, QuantidadePontosInvalidoException, SolicitacaoTrocaException {

        validaRegrasParaTroca(trocaItemDTO);
        atualizaQuantidadeSolicitacaoTrocaRebelde(recuperaIdsRebeldes(trocaItemDTO), SOMA);
        return salvarSolicitacao(trocaItemDTO);

    }

    private void atualizaQuantidadeSolicitacaoTrocaRebelde(List<Long> idsRebeldes, char operacao) throws SolicitacaoTrocaException {
        rebeldeService.solicitaTroca(idsRebeldes, operacao);
    }

    private Long salvarSolicitacao(TrocaItemDTO trocaItemDTO){
        trocaItemDTO.setStatusSolicitacaoTroca(StatusTroca.PENDENTE.toString());
        TrocaItem trocaItem = trocaItemMapper.toModel(trocaItemDTO);
        TrocaItem trocaItemSave = trocaItemRepository.save(trocaItem);
        return trocaItemSave.getId();
    }


    public void validaRegrasParaTroca(TrocaItemDTO trocaItemDTO) throws TraidorException,
            QuantidadeRebeldeParaTrocaException, ItensInvalidosParaTrocaException, QuantidadePontosInvalidoException {

        List<Long> rebeldeListId = recuperaIdsRebeldes(trocaItemDTO);
        List<RebeldeDTO> rebeldeList = rebeldeService.getRebeldeByListId(rebeldeListId);

        hasTraidor(rebeldeList);
        verificaQuantidadeRebeldesParaTroca(rebeldeList);
        validaItensParaTroca(rebeldeList, trocaItemDTO);
        validaPontosParaTroca(trocaItemDTO);

    }

    public void efetuaTroca(Long id) throws RebeldeNotFoundException, StatusTrocaException, TrocaItemNotFoundException, SolicitacaoTrocaException {

        Set<InventarioDTO> rebeldeSolicitanteSet = new HashSet<>();
        Set<InventarioDTO> rebeldeSolicitadoSet = new HashSet<>();

        TrocaItemDTO trocaItemDTO = verifyIsExistsTroca(id);
        verificaStatusTroca(trocaItemDTO);

        RebeldeDTO rebeldeSolicitante = rebeldeService.verifyIsExistsRebelde(trocaItemDTO.getIdRebeldeSolicitante());
        RebeldeDTO rebeldeSolicitado = rebeldeService.verifyIsExistsRebelde(trocaItemDTO.getIdRebeldeSolicitado());

        rebeldeSolicitanteSet = rebeldeSolicitante.getInventario().stream().collect(Collectors.toSet());
        rebeldeSolicitadoSet = rebeldeSolicitado.getInventario().stream().collect(Collectors.toSet());

        Set<InventarioDTO> inventarioSolicitanteAux = calculaTrocaItens(rebeldeSolicitanteSet,trocaItemDTO.getListItensRebeldeSolicitante(), SUBTRACAO);
        Set<InventarioDTO> inventarioSolicitadoAux = calculaTrocaItens(rebeldeSolicitadoSet,trocaItemDTO.getListItensRebeldeSolicitado(), SUBTRACAO);

        Set<InventarioDTO> inventarioSolicitanteSave = calculaTrocaItens(inventarioSolicitanteAux,trocaItemDTO.getListItensRebeldeSolicitado(), SOMA);
        Set<InventarioDTO> inventarioSolicitadoSave = calculaTrocaItens(inventarioSolicitadoAux,trocaItemDTO.getListItensRebeldeSolicitante(), SOMA);

        atualizaDadosRebelde(rebeldeSolicitante, inventarioSolicitanteSave);
        atualizaDadosRebelde(rebeldeSolicitado, inventarioSolicitadoSave);

        atualizaStatusTroca(trocaItemDTO, StatusTroca.ACEITO.toString());
        atualizaQuantidadeSolicitacaoTrocaRebelde(recuperaIdsRebeldes(trocaItemDTO), SUBTRACAO);
    }

    private void atualizaDadosRebelde(RebeldeDTO rebeldeDTO, Set<InventarioDTO> listInventario){
        List<InventarioDTO> listInventarioDTO = new ArrayList<InventarioDTO>(listInventario);
        rebeldeDTO.setInventario(listInventarioDTO);
        rebeldeService.save(rebeldeDTO);
    }

    private void atualizaStatusTroca(TrocaItemDTO trocaItemDTO, String statusTroca){
         trocaItemDTO.setStatusSolicitacaoTroca(statusTroca);
         trocaItemRepository.save(trocaItemMapper.toModel(trocaItemDTO));
    }

    private void verificaStatusTroca(TrocaItemDTO trocaItemDTO) throws StatusTrocaException {
        if (trocaItemDTO.getStatusSolicitacaoTroca().equals(StatusTroca.RECUSADO.getDescricao())){
            throw new StatusTrocaException(trocaItemDTO.getStatusSolicitacaoTroca());
        }
    }

    public Set<InventarioDTO> calculaTrocaItens(Set<InventarioDTO> inventarioRebelde, List<InventarioDTO> listItensTroca, char operador){
        Set<InventarioDTO> inventarioList = new HashSet<>();
        List<InventarioDTO> listaAux = new ArrayList<>();

        for (InventarioDTO itemInventario: inventarioRebelde){
            for (InventarioDTO itemInventarioTroca: listItensTroca){
                if (itemInventario.getItemInventario().equals(itemInventarioTroca.getItemInventario())){
                    itemInventario.setQuantidade(calculate(itemInventario.getQuantidade(), itemInventarioTroca.getQuantidade(), operador));
                    listaAux.add(itemInventario);
                }else{
                    if (!inventarioList.contains(itemInventario.getItemInventario())){
                        listaAux.add(itemInventario);
                    }
                }
            }
        }
        inventarioList = listaAux.stream().collect(Collectors.toSet());
        return inventarioList;
    }

    public TrocaItemDTO verifyIsExistsTroca(Long id) throws TrocaItemNotFoundException {
        return trocaItemRepository.findById(id).map(trocaItemMapper::toDTO)
                .orElseThrow(() -> new TrocaItemNotFoundException(id));
    }

    public void verificaQuantidadeRebeldesParaTroca(List<RebeldeDTO> rebeldeList) throws QuantidadeRebeldeParaTrocaException {
        if(rebeldeList.stream().count() != 2 ){
            throw new QuantidadeRebeldeParaTrocaException();
        }
    }

    public void hasTraidor(List<RebeldeDTO> rebeldeList) throws TraidorException {
        boolean hasTraidor = rebeldeList.stream()
                .anyMatch( rebeldeDTO -> {
                    return rebeldeDTO.isTraidor();
                });
        if (hasTraidor){
            throw new TraidorException();
        }
    }

    public void validaItensParaTroca(List<RebeldeDTO> rebeldeList, TrocaItemDTO trocaItemDTO) throws ItensInvalidosParaTrocaException {

        for (RebeldeDTO rebeldeItem: rebeldeList) {
            if (rebeldeItem.getId().equals(trocaItemDTO.getIdRebeldeSolicitante())){
                compararListItensParaTroca(rebeldeItem.getInventario(),
                        trocaItemDTO.getListItensRebeldeSolicitante(), "Solicitante");
            }else{
                compararListItensParaTroca(rebeldeItem.getInventario(),
                        trocaItemDTO.getListItensRebeldeSolicitado(), "Solicitado");
            }
        }
    }

    private void compararListItensParaTroca(List<InventarioDTO> inventarioRebelde, List<InventarioDTO> listItensTroca,
                                            String perfilSolicitacao) throws ItensInvalidosParaTrocaException {
        long count = 0L;
        count = inventarioRebelde.stream().filter(inventario -> {
            return listItensTroca.stream().filter(itensList -> itensList.getItemInventario().equals(inventario.getItemInventario())).findAny().isPresent();
        }).count();

        if (count != listItensTroca.size()){
            throw new ItensInvalidosParaTrocaException(perfilSolicitacao);
        }
    }

    public void validaPontosParaTroca(TrocaItemDTO trocaItemDTO) throws QuantidadePontosInvalidoException {
        Integer somaPontosSolicitante = 0, somaPontosSolicitado  = 0;
        somaPontosSolicitante = somaPontosItem(trocaItemDTO.getListItensRebeldeSolicitante());
        somaPontosSolicitado = somaPontosItem(trocaItemDTO.getListItensRebeldeSolicitado());

        if (!somaPontosSolicitante.equals(somaPontosSolicitado)){
            throw new QuantidadePontosInvalidoException();
        }
    }

    public Integer somaPontosItem(List<InventarioDTO> listItemParaTroca){
        Integer soma = 0;
        for(InventarioDTO itemInventario : listItemParaTroca){
            soma += defineBaseCalculoParaTroca(itemInventario);
        }
        return soma;
    }

    public Integer defineBaseCalculoParaTroca(InventarioDTO inventarioItem) {
        switch (inventarioItem.getItemInventario()){
            case "ARMA":
                return calculate(ARMA, inventarioItem.getQuantidade(), '*');
            case "MUNICAO":
                return calculate(MUNICAO, inventarioItem.getQuantidade(), '*');
            case "AGUA":
                return calculate(AGUA, inventarioItem.getQuantidade(), '*');
            case "COMIDA":
                return calculate(COMIDA, inventarioItem.getQuantidade(), '*');
        }
        return 0;
    }

    private Integer calculate(Integer baseCalculo, Integer quantidade, char operador){

        switch (operador){
            case SOMA:
                return baseCalculo + quantidade;
            case SUBTRACAO:
                return baseCalculo - quantidade;
            case MULTIPLICACAO:
                return baseCalculo * quantidade;
            case DIVISAO:
                return baseCalculo / quantidade;
            default:
                return 0;
        }
    }

    public List<Long> recuperaIdsRebeldes(TrocaItemDTO trocaItemDTO){
        return Arrays.asList(trocaItemDTO.getIdRebeldeSolicitante(), trocaItemDTO.getIdRebeldeSolicitado());
    }

    public List<TrocaItemDTO> searchSolicitacaoTrocaEnviada(Long id) {
        return trocaItemRepository.findByIdRebeldeSolicitante(id)
                .stream().map(trocaItemMapper::toDTO).collect(Collectors.toList());
    }

    public List<TrocaItemDTO> searchSolicitacaoRecebida(Long id) {
        return trocaItemRepository.findByIdRebeldeSolicitado(id)
                .stream().map(trocaItemMapper::toDTO).collect(Collectors.toList());
    }

    public void rejeitaTroca(Long id) throws TrocaItemNotFoundException, SolicitacaoTrocaException {
        verifyIsExistsTroca(id);
        Optional<Object> trocaItemDTO = trocaItemRepository.findById(id)
                .map(trocaItem -> {
                    trocaItem.setStatusSolicitacaoTroca(StatusTroca.RECUSADO);
                    return trocaItemRepository.save(trocaItem);
                });
        if (trocaItemDTO.isPresent()){
            TrocaItemDTO trocaItem = TrocaItemDTO.builder().build();
            atualizaQuantidadeSolicitacaoTrocaRebelde(recuperaIdsRebeldes(trocaItem), SUBTRACAO);
        }
    }
}
