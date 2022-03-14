package br.com.starwarsresistence.galaticwar.dto.request;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RebeldeDTO {

    private Long id;

    @NotEmpty
    private String nome;
    @NotEmpty
    private Integer idade;
    @NotEmpty
    private String genero;
    @Valid
    @NotEmpty
    private LocalizacaoDTO localizacao;
    @Valid
    @NotEmpty
    private List<InventarioDTO> inventario;

    private Integer reportTraidor;
    private boolean traidor;
    private Integer quantidadeSolicitacao;
    private boolean hasSolicitacaoTroca;

}
