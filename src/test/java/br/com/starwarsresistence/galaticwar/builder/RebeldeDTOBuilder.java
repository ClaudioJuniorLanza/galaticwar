package br.com.starwarsresistence.galaticwar.builder;

import br.com.starwarsresistence.galaticwar.domain.Inventario;
import br.com.starwarsresistence.galaticwar.dto.request.InventarioDTO;
import br.com.starwarsresistence.galaticwar.dto.request.LocalizacaoDTO;
import br.com.starwarsresistence.galaticwar.dto.request.RebeldeDTO;
import br.com.starwarsresistence.galaticwar.enums.Genero;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public class RebeldeDTOBuilder {

    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String nome = "RebeldeTeste";
    @Builder.Default
    private Integer idade = 55;
    @Builder.Default
    private String genero = Genero.MASCULINO.toString();
    @Builder.Default
    private LocalizacaoDTO localizacao = new LocalizacaoDTO("LugarNenhum", "10 º W", "10 º S");
    @Builder.Default
    private List<InventarioDTO> inventario = Arrays.asList(new InventarioDTO(2L, "MUNICAO", 2),
            new InventarioDTO(3L, "AGUA", 2), new InventarioDTO(4L, "COMIDA", 2));
    @Builder.Default
    private Integer reportTraidor = 0;
    @Builder.Default
    private boolean traidor = false;
    @Builder.Default
    private Integer quantidadeSolicitacao = 0;
    @Builder.Default
    private boolean hasSolicitacaoTroca = false;

    public RebeldeDTO toRebeldeDTO(){
        return new RebeldeDTO(
              id,
              nome,
              idade,
              genero,
              localizacao,
              inventario,
              reportTraidor,
              traidor,
              quantidadeSolicitacao,
              hasSolicitacaoTroca
        );
    }
}
