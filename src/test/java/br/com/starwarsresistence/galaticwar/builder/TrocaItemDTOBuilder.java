package br.com.starwarsresistence.galaticwar.builder;

import br.com.starwarsresistence.galaticwar.dto.request.InventarioDTO;
import br.com.starwarsresistence.galaticwar.dto.request.TrocaItemDTO;
import br.com.starwarsresistence.galaticwar.enums.StatusTroca;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public class TrocaItemDTOBuilder {

    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private Long idRebeldeSolicitante = 3L;
    @Builder.Default
    private Long idRebeldeSolicitado = 4L;
    @Builder.Default
    private List<InventarioDTO> listItensRebeldeSolicitante = Arrays.asList(new InventarioDTO(2L, "MUNICAO", 2),
            new InventarioDTO(3L, "AGUA", 2), new InventarioDTO(4L, "COMIDA", 2), new InventarioDTO(5L, "ARMA", 2));
    @Builder.Default
    private List<InventarioDTO> listItensRebeldeSolicitado = Arrays.asList(new InventarioDTO(6L, "MUNICAO", 3),
            new InventarioDTO(7L, "AGUA", 3), new InventarioDTO(8L, "COMIDA", 3), new InventarioDTO(9L, "COMIDA", 3));
    @Builder.Default
    private String statusSolicitacaoTroca = StatusTroca.PENDENTE.toString();

    public TrocaItemDTO toTrocaItemDTO(){
        return new TrocaItemDTO(
                id,
                idRebeldeSolicitante,
                idRebeldeSolicitado,
                listItensRebeldeSolicitante,
                listItensRebeldeSolicitado,
                statusSolicitacaoTroca
        );
    }

}
