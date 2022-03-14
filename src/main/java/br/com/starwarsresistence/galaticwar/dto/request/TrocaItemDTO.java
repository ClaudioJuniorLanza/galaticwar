package br.com.starwarsresistence.galaticwar.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrocaItemDTO {

    private Long id;

    @NotEmpty
    private Long idRebeldeSolicitante;
    @NotEmpty
    private Long idRebeldeSolicitado;
    @NotEmpty
    private List<InventarioDTO> listItensRebeldeSolicitante;
    @NotEmpty
    private List<InventarioDTO> listItensRebeldeSolicitado;

    private String statusSolicitacaoTroca;
}
