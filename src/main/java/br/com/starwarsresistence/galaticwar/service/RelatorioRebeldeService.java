package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.dto.mapper.RebeldeMapper;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.dto.response.MessageResponse;
import br.com.starwarsresistence.galaticwar.repository.RebeldeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RelatorioRebeldeService {

    private final RebeldeRepository rebeldeRepository;
    private final RebeldeMapper rebeldeMapper;

    private static final String TEXTO_TRAIDORES = "Porcentagem de traidores: ";
    private static final String TEXTO_REBELDES = "Porcentagem de Rebeldes: ";

    public MessageResponse getPorcentagemTraidores() {
        List<RebeldeDTO> rebeldeList = getRebeldeList();
        Long qtdTraidores = rebeldeList.stream().filter(rebeldeDTO -> rebeldeDTO.isTraidor()).count();
        Long porcentagemTraidores = calculate(qtdTraidores, rebeldeList.size());
        return createMesageResponse(TEXTO_TRAIDORES, porcentagemTraidores);
    }

    public MessageResponse getPorcentagemRebeldes() {
        List<RebeldeDTO> rebeldeList = getRebeldeList();
        Long qtdRebeldesAtivos = rebeldeList.stream().filter(rebeldeDTO -> !rebeldeDTO.isTraidor()).count();
        Long porcentagemRebeldes = calculate(qtdRebeldesAtivos, rebeldeList.size());
        return createMesageResponse(TEXTO_REBELDES, porcentagemRebeldes);
    }

    private List<RebeldeDTO> getRebeldeList() {
        return rebeldeRepository.findAll()
                .stream().map(rebeldeMapper::toDTO).collect(Collectors.toList());
    }

    private Long calculate(Long quantidade, Integer total) {
        long porcentagemTraidores = (quantidade*100)/total;
        return porcentagemTraidores;
    }

    private MessageResponse createMesageResponse(String s, Long valor) {
        return MessageResponse.builder()
                .message(s + valor)
                .build();
    }

}
