package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.domain.Rebelde;
import br.com.starwarsresistence.galaticwar.dto.mapper.RebeldeMapper;
import br.com.starwarsresistence.galaticwar.dto.request.LocalizacaoDTO;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.exceptions.RebeldeNotFoundException;
import br.com.starwarsresistence.galaticwar.exceptions.SolicitacaoTrocaException;
import br.com.starwarsresistence.galaticwar.repository.RebeldeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RebeldeService {

    private static final Integer MAX_RELATED_TRAIDOR = 2;
    private static final Integer VALOR_CONTROLE = 1;

    private final RebeldeRepository rebeldeRepository;
    private final RebeldeMapper rebeldeMapper = RebeldeMapper.INSTANCE;

    public List<RebeldeDTO> findAll(Pageable pageable){
        return rebeldeRepository.findAll(pageable)
                .stream()
                .map(rebeldeMapper::toDTO)
                .map(rebeldeDTO -> {
                    if (rebeldeDTO.getQuantidadeSolicitacao() > 0) {
                        rebeldeDTO.setHasSolicitacaoTroca(true);
                    } else {
                        rebeldeDTO.setHasSolicitacaoTroca(false);
                    }
                    return rebeldeDTO;
                }).collect(Collectors.toList());
    }

    public Optional<RebeldeDTO> findById(Long id) throws RebeldeNotFoundException {
        return Optional.ofNullable(rebeldeRepository.findById(id)
                .map(rebeldeMapper::toDTO)
                .map(rebeldeDTO -> {
                    if (rebeldeDTO.getQuantidadeSolicitacao() > 0) {
                        rebeldeDTO.setHasSolicitacaoTroca(true);
                    } else {
                        rebeldeDTO.setHasSolicitacaoTroca(false);
                    }
                    return rebeldeDTO;
                }).orElseThrow(() -> new RebeldeNotFoundException(id)));
    }

    public RebeldeDTO save(RebeldeDTO rebeldeDTO) {
        Rebelde rebeldeConverter = rebeldeMapper.toModel(rebeldeDTO);
        Rebelde rebeldeCreated = rebeldeRepository.save(rebeldeConverter);
        return rebeldeMapper.toDTO(rebeldeCreated);
    }

    public void relatedTraidor(Long id) throws RebeldeNotFoundException {
        rebeldeRepository.findById(id)
                .map( rebelde -> {
                    rebelde.setReportTraidor(rebelde.getReportTraidor() + VALOR_CONTROLE);
                    return rebeldeRepository.save(rebelde);
                }).orElseThrow( () -> new RebeldeNotFoundException(id));
        verifyIsTraidor(id);
    }

    public void updateLocalizacao(Long id, LocalizacaoDTO localizacaoDTO) throws RebeldeNotFoundException {
        RebeldeDTO rebeldeDTO = verifyIsExistsRebelde(id);
        rebeldeDTO.setLocalizacao(localizacaoDTO);
        Rebelde rebelde = rebeldeMapper.toModel(rebeldeDTO);
        rebeldeRepository.save(rebelde);
    }

    public void atualizaQuantidadeSolicitacaoTroca(List<Long> listRebeldeId, char operacao) throws SolicitacaoTrocaException {
        try{
            rebeldeRepository.findByIdIn(listRebeldeId).stream()
                    .map( rebelde -> {
                        switch (operacao){
                            case '+':
                                rebelde.setQuantidadeSolicitacao(rebelde.getQuantidadeSolicitacao() + VALOR_CONTROLE);
                                break;
                            case '-':
                                rebelde.setQuantidadeSolicitacao(rebelde.getQuantidadeSolicitacao() - VALOR_CONTROLE);
                                break;
                        }
                        return rebeldeRepository.save(rebelde);
                    }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SolicitacaoTrocaException();
        }
    }

    public RebeldeDTO verifyIsExistsRebelde(Long id) throws RebeldeNotFoundException {
        return rebeldeRepository.findById(id).map(rebeldeMapper::toDTO)
                .orElseThrow(() -> new RebeldeNotFoundException(id));
    }

    public List<RebeldeDTO> getRebeldeByListId(List<Long> listRebeldeId){
        return rebeldeRepository.findByIdIn(listRebeldeId)
                .stream()
                .map(rebeldeMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void verifyIsTraidor(Long id) {
        rebeldeRepository.findById(id)
                .map( rebelde -> {
                    if (rebelde.getReportTraidor() > MAX_RELATED_TRAIDOR)
                        rebelde.setTraidor(true);
                    return rebeldeRepository.save(rebelde);
                });
    }
}
