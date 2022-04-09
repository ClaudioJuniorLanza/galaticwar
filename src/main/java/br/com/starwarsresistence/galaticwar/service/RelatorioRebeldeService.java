package br.com.starwarsresistence.galaticwar.service;

import br.com.starwarsresistence.galaticwar.dto.request.InventarioDTO;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.dto.response.MessageResponse;
import br.com.starwarsresistence.galaticwar.enums.ItensInventario;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RelatorioRebeldeService {

    private final RebeldeService rebeldeService;
    private final InventarioService inventarioService;

    private static final String REBELDE = "REBELDE";
    private static final String TRAIDOR = "TRAIDOR";

    private static final String TEXTO_TRAIDORES = "Porcentagem de traidores: ";
    private static final String TEXTO_REBELDES = "Porcentagem de Rebeldes: ";

    public MessageResponse getPorcentagemRebedesOrTraidores(String tipoFiltro) {
        List<RebeldeDTO> rebeldeList = getRebeldeList();
        Long quantidade = 0L;
        String textoResponse = "";

        switch (tipoFiltro){
            case REBELDE:
                quantidade = rebeldeList.stream().filter(rebeldeDTO -> !rebeldeDTO.isTraidor()).count();
                textoResponse = TEXTO_REBELDES;
                break;
            case TRAIDOR:
                    quantidade = rebeldeList.stream().filter(RebeldeDTO::isTraidor).count();
                    textoResponse = TEXTO_TRAIDORES;
                    break;
        }
        Double porcentagem = calculate(quantidade, rebeldeList.size());
        return createMesageResponse(textoResponse, porcentagem);
    }

    public List<MessageResponse> getMediaRecursos() {
        List<InventarioDTO> inventarioDTOList = inventarioService.findAll();
        Double mediaArma = obtemMediaItensInventario(inventarioDTOList, ItensInventario.ARMA.toString());
        Double mediaMunicao = obtemMediaItensInventario(inventarioDTOList, ItensInventario.MUNICAO.toString());
        Double mediaAgua = obtemMediaItensInventario(inventarioDTOList, ItensInventario.AGUA.toString());
        Double mediaComida = obtemMediaItensInventario(inventarioDTOList, ItensInventario.COMIDA.toString());

        List<MessageResponse> messageList = new ArrayList<>();
        messageList.add(createMesageResponse("Média de armas por rebelde:", mediaArma));
        messageList.add(createMesageResponse("Média de munição por rebelde:", mediaMunicao));
        messageList.add(createMesageResponse("Média de água por rebelde:", mediaAgua));
        messageList.add(createMesageResponse("Média de comida por rebelde:", mediaComida));

        return messageList;
    }

    private double obtemMediaItensInventario(List<InventarioDTO> inventarioDTOList, String tipoEquipamento) {
        return inventarioDTOList.stream()
                .filter(inventarioDTO -> inventarioDTO.getItemInventario().equals(tipoEquipamento))
                .mapToInt(InventarioDTO::getQuantidade)
                .average()
                .getAsDouble();
    }

    private List<RebeldeDTO> getRebeldeList() {
        Pageable pageable = null;
        return rebeldeService.findAll(pageable);
    }

    private Double calculate(Long quantidade, Integer total) {
        return Double.valueOf((quantidade*100)/total);
    }

    private MessageResponse createMesageResponse(String s, Double valor) {
        return MessageResponse.builder()
                .message(s + valor)
                .build();
    }

}
